package top.inept.suiji.feature.tag.domain.vo

import io.swagger.v3.oas.annotations.media.Schema

data class TagVO(
    @Schema(description = "openapi.common.id")
    val id: Long,

    @Schema(description = "openapi.tag.name")
    val name: String,
)