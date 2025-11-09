package top.inept.suiji.feature.note.domian.dto

import io.swagger.v3.oas.annotations.media.Schema
import top.inept.suiji.base.BaseQueryDTO

data class QueryNoteDTO(
    @Schema(description = "openapi.note.title")
    val title: String?,

    @Schema(description = "openapi.note.public_id")
    val publicId: String?,

    @Schema(description = "openapi.note.category")
    val category: Long?,

    @Schema(description = "openapi.note.tags")
    val tags: List<Long>?,
) : BaseQueryDTO()