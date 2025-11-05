package top.inept.suiji.feature.category.service

import org.springframework.data.domain.Page
import top.inept.suiji.feature.category.domain.dto.CreateCategoryDTO
import top.inept.suiji.feature.category.domain.dto.QueryCategoryDTO
import top.inept.suiji.feature.category.domain.dto.UpdateCategoryDTO
import top.inept.suiji.feature.category.domain.entity.Category

interface CategoryService {
    fun getCategory(queryCategoryDTO: QueryCategoryDTO): Page<Category>
    fun getCategoryById(id: Long): Category
    fun createCategory(dto: CreateCategoryDTO): Category
    fun deleteCategory(id: Long)
    fun updateCategory(dto: UpdateCategoryDTO): Category
}