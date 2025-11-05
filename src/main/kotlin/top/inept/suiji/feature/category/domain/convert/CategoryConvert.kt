package top.inept.suiji.feature.category.domain.convert

import top.inept.suiji.feature.category.domain.dto.CreateCategoryDTO
import top.inept.suiji.feature.category.domain.dto.UpdateCategoryDTO
import top.inept.suiji.feature.category.domain.entity.Category
import top.inept.suiji.feature.category.domain.vo.CategoryVO

fun CreateCategoryDTO.toCategory() = Category().also {
    it.name = this.name
}

fun UpdateCategoryDTO.toCategory() = Category().also {
    it.id = this.id
    it.name = this.name
}

fun Category.toCategoryVO() = CategoryVO(
    id = this.id ?: 0L,
    name = this.name
)