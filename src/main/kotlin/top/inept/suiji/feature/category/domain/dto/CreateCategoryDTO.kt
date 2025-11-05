package top.inept.suiji.feature.category.domain.dto

import io.swagger.v3.oas.annotations.media.Schema
import top.inept.suiji.feature.category.domain.validated.ValidatedCategoryName

data class CreateCategoryDTO(
    @Schema(description = "openapi.category.name")
    @field:ValidatedCategoryName
    val name: String,
)