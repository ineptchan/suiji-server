package top.inept.suiji.feature.tag

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import top.inept.suiji.exception.business.EntityNotFoundException
import top.inept.suiji.feature.tag.domain.dto.CreateTagDTO
import top.inept.suiji.feature.tag.domain.dto.QueryTagDTO
import top.inept.suiji.feature.tag.domain.dto.UpdateTagDTO
import top.inept.suiji.feature.tag.service.TagService

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TagServiceTest {
    @Autowired
    lateinit var service: TagService

    @Test
    @Order(1)
    fun `test create tag`() {
        val dto = CreateTagDTO(name = "Anime")
        val tag = service.createTag(dto)
        assertThat(tag.id).isNotNull()
        assertThat(tag.name).isEqualTo("Anime")
    }

    @Test
    @Order(2)
    fun `test query tag`() {
        service.createTag(CreateTagDTO("Game"))
        val page = service.getTags(QueryTagDTO(name = ""))
        assertThat(page.totalElements).isGreaterThanOrEqualTo(1)
    }

    @Test
    @Order(3)
    fun `test update tag`() {
        val created = service.createTag(CreateTagDTO("Coding"))

        val updated = service.updateTag(
            created.id!!,
            UpdateTagDTO(
                name = "Programming"
            )
        )
        assertThat(updated.name).isEqualTo("Programming")
    }

    @Test
    @Order(4)
    fun `test get tag by id`() {
        val created = service.createTag(CreateTagDTO("Music"))
        val tag = service.getTagById(created.id!!)
        assertThat(tag.name).isEqualTo("Music")
    }

    @Test
    @Order(5)
    fun `test delete tag`() {
        val created = service.createTag(CreateTagDTO("DeleteMe"))

        service.deleteTag(created.id!!)
        assertThatThrownBy {
            service.getTagById(created.id!!)
        }.isInstanceOf(EntityNotFoundException::class.java)
    }
}
