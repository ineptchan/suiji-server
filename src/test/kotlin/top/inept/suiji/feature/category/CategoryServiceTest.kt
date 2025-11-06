package top.inept.suiji.feature.category


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
import top.inept.suiji.feature.category.domain.dto.CreateCategoryDTO
import top.inept.suiji.feature.category.domain.dto.QueryCategoryDTO
import top.inept.suiji.feature.category.domain.dto.UpdateCategoryDTO
import top.inept.suiji.feature.category.service.CategoryService

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class CategoryServiceTest {
    @Autowired
    lateinit var service: CategoryService

    @Test
    @Order(1)
    fun `test create category`() {
        val dto = CreateCategoryDTO(name = "Anime")
        val category = service.createCategory(dto)
        assertThat(category.id).isNotNull()
        assertThat(category.name).isEqualTo("Anime")
    }

    @Test
    @Order(2)
    fun `test query category`() {
        service.createCategory(CreateCategoryDTO("Game"))
        val page = service.getCategory(QueryCategoryDTO(name = ""))
        assertThat(page.totalElements).isGreaterThanOrEqualTo(1)
    }

    @Test
    @Order(3)
    fun `test update category`() {
        val created = service.createCategory(CreateCategoryDTO("Coding"))

        val updated = service.updateCategory(
            created.id!!,
            UpdateCategoryDTO(
                name = "Programming"
            )
        )
        assertThat(updated.name).isEqualTo("Programming")
    }

    @Test
    @Order(4)
    fun `test get category by id`() {
        val created = service.createCategory(CreateCategoryDTO("Music"))
        val category = service.getCategoryById(created.id!!)
        assertThat(category.name).isEqualTo("Music")
    }

    @Test
    @Order(5)
    fun `test delete category`() {
        val created = service.createCategory(CreateCategoryDTO("DeleteMe"))

        service.deleteCategory(created.id!!)
        assertThatThrownBy {
            service.getCategoryById(created.id!!)
        }.isInstanceOf(EntityNotFoundException::class.java)
    }
}