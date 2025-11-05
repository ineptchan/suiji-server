package top.inept.suiji.feature.tag.domain.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.PositiveOrZero
import top.inept.suiji.feature.tag.domain.validated.ValidatedTagName

data class UpdateTagDTO(
    @Schema(description = "openapi.common.id")
    @field:PositiveOrZero(message = "valid.common.id")
    val id: Long,

    @Schema(description = "openapi.tag.name")
    @field:ValidatedTagName
    val name: String,
)