package top.inept.suiji.feature.note

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import top.inept.suiji.exception.business.EntityNotFoundException
import top.inept.suiji.feature.category.domain.dto.CreateCategoryDTO
import top.inept.suiji.feature.category.service.CategoryService
import top.inept.suiji.feature.note.domian.dto.CreateNoteDTO
import top.inept.suiji.feature.note.domian.dto.QueryNoteDTO
import top.inept.suiji.feature.note.domian.dto.UpdateNoteDTO
import top.inept.suiji.feature.note.service.NoteService
import top.inept.suiji.feature.tag.domain.dto.CreateTagDTO
import top.inept.suiji.feature.tag.service.TagService
import java.util.*

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class NoteServiceTest {

    @Autowired
    lateinit var noteService: NoteService

    @Autowired
    lateinit var categoryService: CategoryService

    @Autowired
    lateinit var tagService: TagService

    private var seedCategoryId: Long = 0L
    private var seedTagIds: List<Long> = emptyList()

    @BeforeEach
    fun setupSeedData() {
        val category = categoryService.createCategory(CreateCategoryDTO(name = "seed-category"))
        seedCategoryId = category.id!!

        val tag1 = tagService.createTag(CreateTagDTO(name = "seed-tag-1"))
        val tag2 = tagService.createTag(CreateTagDTO(name = "seed-tag-2"))
        seedTagIds = listOf(tag1.id!!, tag2.id!!)
    }

    @Test
    @Order(2)
    fun `test create note`() {
        val dto = CreateNoteDTO(
            title = "My first note",
            content = "Content A",
            category = seedCategoryId,
            tags = seedTagIds
        )
        val note = noteService.createNote(dto)

        assertThat(note.id).isNotNull()
        assertThat(note.title).isEqualTo("My first note")
        assertThat(note.publicId).isNotNull()
        // validate publicId is a valid UUID string
        UUID.fromString(note.publicId)
    }

    @Test
    @Order(3)
    fun `test create note without publicId generates uuid`() {
        val dto = CreateNoteDTO(
            title = "Note without publicId",
            content = "Content B",
            category = seedCategoryId,
            tags = null
        )
        val note = noteService.createNote(dto)

        assertThat(note.id).isNotNull()
        assertThat(note.title).isEqualTo("Note without publicId")
        assertThat(note.publicId).isNotNull()
        UUID.fromString(note.publicId)
    }

    @Test
    @Order(4)
    fun `test query notes by title and pagination`() {
        noteService.createNote(
            CreateNoteDTO(
                title = "QueryTitleA",
                content = "c1",
                category = seedCategoryId,
                tags = seedTagIds
            )
        )
        noteService.createNote(
            CreateNoteDTO(
                title = "QueryTitleB",
                content = "c2",
                category = seedCategoryId,
                tags = seedTagIds
            )
        )

        val pageDto = QueryNoteDTO(title = "QueryTitle", publicId = null, category = null, tags = null).apply {
            page = 1
            size = 10
        }
        val page = noteService.getNotes(pageDto)
        assertThat(page.totalElements).isGreaterThanOrEqualTo(2)
    }

    @Test
    @Order(5)
    fun `test query notes by category and tags`() {
        noteService.createNote(
            CreateNoteDTO(
                title = "CatNote1",
                content = "x",
                category = seedCategoryId,
                tags = listOf(seedTagIds[0])
            )
        )
        noteService.createNote(
            CreateNoteDTO(
                title = "CatNote2",
                content = "y",
                category = seedCategoryId,
                tags = seedTagIds
            )
        )

        val dto =
            QueryNoteDTO(title = null, publicId = null, category = seedCategoryId, tags = listOf(seedTagIds[0])).apply {
                page = 1
                size = 20
            }
        val page = noteService.getNotes(dto)
        assertThat(page.totalElements).isGreaterThanOrEqualTo(2)
        page.content.forEach { assertThat(it.category.id).isEqualTo(seedCategoryId) }
    }

    @Test
    @Order(6)
    fun `test get note by id and by publicId`() {
        val created = noteService.createNote(
            CreateNoteDTO(
                title = "GetMe",
                content = "z",
                category = seedCategoryId,
                tags = seedTagIds
            )
        )
        val byId = noteService.getNoteById(created.id)
        assertThat(byId.title).isEqualTo("GetMe")

        val byPublic = noteService.getNoteByPublicId(created.publicId)
        assertThat(byPublic.id).isEqualTo(created.id)
    }

    @Test
    @Order(7)
    fun `test update note partial fields`() {
        val created = noteService.createNote(
            CreateNoteDTO(
                title = "OldTitle",
                content = "old",
                category = seedCategoryId,
                tags = seedTagIds
            )
        )
        val updateDto = UpdateNoteDTO(
            publicId = null,
            title = "NewTitle",
            content = "new content",
            category = null,
            tags = null
        )
        val updated = noteService.updateNote(created.id, updateDto)

        assertThat(updated.title).isEqualTo("NewTitle")
        assertThat(updated.content).isEqualTo("new content")
        assertThat(updated.category.id).isEqualTo(seedCategoryId)
    }

    @Test
    @Order(8)
    fun `test delete note returns true and then not found`() {
        val created = noteService.createNote(
            CreateNoteDTO(
                title = "ToDelete",
                content = "d",
                category = seedCategoryId,
                tags = seedTagIds
            )
        )
        val deleted = noteService.deleteNote(created.id)
        assertThat(deleted).isTrue()

        assertThatThrownBy { noteService.getNoteById(created.id) }
            .isInstanceOf(EntityNotFoundException::class.java)
    }

    @Test
    @Order(9)
    fun `test delete note by publicId`() {
        val created = noteService.createNote(
            CreateNoteDTO(
                title = "ToDeletePub",
                content = "d2",
                category = seedCategoryId,
                tags = seedTagIds
            )
        )
        // use created.publicId to delete the same note
        noteService.deleteNoteByPublicId(created.publicId)

        assertThatThrownBy { noteService.getNoteByPublicId(created.publicId) }
            .isInstanceOf(EntityNotFoundException::class.java)
    }

    @Test
    @Order(10)
    fun `test get non existing note throws`() {
        assertThatThrownBy { noteService.getNoteById(9999999L) }
            .isInstanceOf(EntityNotFoundException::class.java)

        assertThatThrownBy { noteService.getNoteByPublicId(UUID.randomUUID().toString()) }
            .isInstanceOf(EntityNotFoundException::class.java)
    }
}
