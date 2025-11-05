package top.inept.suiji.feature.tag.domain.dto

import io.swagger.v3.oas.annotations.media.Schema
import top.inept.suiji.feature.tag.domain.validated.ValidatedTagName

data class CreateTagDTO(
    @Schema(description = "openapi.tag.name")
    @field:ValidatedTagName
    val name: String,
)