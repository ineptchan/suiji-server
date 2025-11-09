package top.inept.suiji.feature.note.domian.dto

import io.swagger.v3.oas.annotations.media.Schema
import top.inept.suiji.feature.note.domian.validated.ValidatedNoteCategory
import top.inept.suiji.feature.note.domian.validated.ValidatedNoteTitle

data class CreateNoteDTO(
    @Schema(description = "openapi.note.title")
    @field:ValidatedNoteTitle
    val title: String,

    @Schema(description = "openapi.note.content")
    val content: String,

    @Schema(description = "openapi.note.category")
    @field:ValidatedNoteCategory
    val category: Long,

    @Schema(description = "openapi.note.tags")
    val tags: List<Long>?,
)