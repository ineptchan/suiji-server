package top.inept.suiji.feature.category

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import top.inept.suiji.feature.category.domain.dto.CreateCategoryDTO
import top.inept.suiji.feature.category.domain.dto.QueryCategoryDTO
import top.inept.suiji.feature.category.service.CategoryService

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CategoryServiceTest {
    @Autowired
    lateinit var service: CategoryService


    @BeforeEach
    @Test
    fun testCreateCategory() {
        val category = service.createCategory(CreateCategoryDTO("test-category-name"))

        println(category)
    }

    @Test
    fun testGetCategorys() {
        val categories = service.getCategory(QueryCategoryDTO("test"))

        println(categories)
    }
}