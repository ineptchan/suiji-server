package top.inept.suiji.feature.category.domain.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.PositiveOrZero
import top.inept.suiji.feature.category.domain.validated.ValidatedCategoryName

data class UpdateCategoryDTO(
    @Schema(description = "openapi.common.id")
    @field:PositiveOrZero(message = "valid.common.id")
    val id: Long,

    @Schema(description = "openapi.category.name")
    @field:ValidatedCategoryName
    val name: String,
)