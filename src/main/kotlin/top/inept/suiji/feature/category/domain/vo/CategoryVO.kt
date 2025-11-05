package top.inept.suiji.feature.category.domain.vo

import io.swagger.v3.oas.annotations.media.Schema

data class CategoryVO(
    @Schema(description = "openapi.common.id")
    val id: Long,

    @Schema(description = "openapi.category.name")
    val name: String,
)