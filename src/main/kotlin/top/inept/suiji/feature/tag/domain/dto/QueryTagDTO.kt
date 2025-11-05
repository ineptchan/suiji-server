package top.inept.suiji.feature.tag.domain.dto

import io.swagger.v3.oas.annotations.media.Schema
import top.inept.suiji.base.BaseQueryDTO

data class QueryTagDTO(
    @Schema(description = "openapi.category.name")
    val name: String,
) : BaseQueryDTO()