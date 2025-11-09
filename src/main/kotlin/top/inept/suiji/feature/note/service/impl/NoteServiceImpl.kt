package top.inept.suiji.feature.note.service.impl

import org.springframework.context.support.MessageSourceAccessor
import org.springframework.data.domain.Page
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.inept.suiji.base.QueryBuilder
import top.inept.suiji.exception.business.DuplicateException
import top.inept.suiji.exception.business.EntityNotFoundException
import top.inept.suiji.extensions.get
import top.inept.suiji.extensions.toPageRequest
import top.inept.suiji.extensions.toUuidOrThrow
import top.inept.suiji.feature.category.domain.convert.toCategoryVO
import top.inept.suiji.feature.category.domain.entity.Category
import top.inept.suiji.feature.category.repository.CategoryRepository
import top.inept.suiji.feature.note.domian.convert.toNote
import top.inept.suiji.feature.note.domian.convert.toNoteVO
import top.inept.suiji.feature.note.domian.dto.CreateNoteDTO
import top.inept.suiji.feature.note.domian.dto.QueryNoteDTO
import top.inept.suiji.feature.note.domian.dto.UpdateNoteDTO
import top.inept.suiji.feature.note.domian.entity.Note
import top.inept.suiji.feature.note.domian.vo.NoteVO
import top.inept.suiji.feature.note.repository.NoteRepository
import top.inept.suiji.feature.note.repository.NoteSpecifications
import top.inept.suiji.feature.note.service.NoteService
import top.inept.suiji.feature.tag.domain.convert.toTagVO
import top.inept.suiji.feature.tag.domain.entity.Tag
import top.inept.suiji.feature.tag.repository.TagRepository

@Service
class NoteServiceImpl(
    private val messages: MessageSourceAccessor,
    private val noteRepository: NoteRepository,
    private val categoryRepository: CategoryRepository,
    private val tagRepository: TagRepository,

    ) : NoteService {
    override fun getNotes(dto: QueryNoteDTO): Page<NoteVO> {
        val pageRequest = dto.toPageRequest()

        val spec = QueryBuilder<Note>()
            .and(NoteSpecifications.titleContainsIgnoreCase(dto.title))
            .and(NoteSpecifications.categoryEquals(dto.category))
            .and(NoteSpecifications.hasTagId(dto.tags))

        dto.publicId?.let {
            if (it.isNotEmpty())
                spec.and(NoteSpecifications.publicIdEquals(it.toUuidOrThrow()))
        }

        val notes = noteRepository.findAll(spec.buildSpec(), pageRequest)

        return notes.map { note ->
            val categoryVO = getCategoryOrThrow(note.category?.id).toCategoryVO()
            val tags = note.tags.map { it.toTagVO() }
            note.toNoteVO(categoryVO, tags)
        }
    }

    override fun getNoteById(id: Long): NoteVO {
        val note = noteRepository.findByIdOrNull(id)
            ?: throw EntityNotFoundException(messages["message.note.not_found"])
        return note.toNoteVO(
            getCategoryOrThrow(note.category?.id).toCategoryVO(),
            note.tags.map { it.toTagVO() }
        )
    }

    override fun getNoteByPublicId(publicId: String): NoteVO {
        val uuid = publicId.toUuidOrThrow()
        val note = noteRepository.findByPublicId(uuid)
            ?: throw EntityNotFoundException(messages["message.note.not_found"])

        return note.toNoteVO(
            getCategoryOrThrow(note.category?.id).toCategoryVO(),
            note.tags.map { it.toTagVO() }
        )
    }

    @Transactional
    override fun createNote(dto: CreateNoteDTO): NoteVO {
        val category = getCategoryOrThrow(dto.category)
        val tags = resolveTagsOrEmpty(dto.tags)

        val note = dto.toNote(category, tags)
        noteRepository.save(note)

        return note.toNoteVO(
            getCategoryOrThrow(note.category?.id).toCategoryVO(),
            note.tags.map { it.toTagVO() }
        )
    }

    override fun deleteNote(id: Long): Boolean {
        noteRepository.deleteById(id)
        return true
    }

    override fun deleteNoteByPublicId(publicId: String) {
        noteRepository.deleteByPublicId(publicId.toUuidOrThrow())
    }

    @Transactional
    override fun updateNote(
        id: Long,
        dto: UpdateNoteDTO
    ): NoteVO {
        val dbNote = noteRepository.findByIdOrNull(id)
            ?: throw EntityNotFoundException(messages["message.note.not_found"])


        dto.publicId?.let {
            val uuid = it.toUuidOrThrow()
            if (dbNote.publicId != uuid) {
                if (noteRepository.existsByPublicId(uuid)) throw DuplicateException(messages["message.note.public_id_duplicate"])
            }
            dbNote.publicId = uuid
        }
        dto.title?.let { dbNote.title = it }
        dto.content?.let { dbNote.content = it }
        dto.category?.let { dbNote.category = getCategoryOrThrow(it) }
        dto.tags?.let { dbNote.tags = resolveTagsOrEmpty(it) }

        noteRepository.save(dbNote)

        return dbNote.toNoteVO(
            getCategoryOrThrow(dbNote.category?.id).toCategoryVO(),
            dbNote.tags.map { it.toTagVO() }
        )
    }

    private fun getCategoryOrThrow(categoryId: Long?): Category {
        if (categoryId == null) throw EntityNotFoundException(messages["message.category.not_found"])

        if (!categoryRepository.existsById(categoryId)) {
            throw EntityNotFoundException(messages["message.category.not_found"])
        }
        return categoryRepository.getReferenceById(categoryId)
    }

    private fun resolveTagsOrEmpty(tagIds: List<Long>?): MutableSet<Tag> {
        if (tagIds.isNullOrEmpty()) return mutableSetOf()

        val distinctIds = tagIds.distinct()
        if (distinctIds.size.toLong() != tagRepository.countByIdIn(distinctIds)) throw EntityNotFoundException(messages["message.tags.not_found"])
        return distinctIds.map { tagRepository.getReferenceById(it) }.toMutableSet()
    }
}