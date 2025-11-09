package top.inept.suiji.feature.note.domian.vo

import io.swagger.v3.oas.annotations.media.Schema
import top.inept.suiji.feature.category.domain.vo.CategoryVO
import top.inept.suiji.feature.tag.domain.vo.TagVO

data class NoteVO(
    @Schema(description = "openapi.common.id")
    val id: Long,

    @Schema(description = "openapi.note.public_id")
    val publicId: String,

    @Schema(description = "openapi.note.title")
    val title: String,

    @Schema(description = "openapi.note.content")
    val content: String,

    @Schema(description = "openapi.note.category")
    val category: CategoryVO,

    @Schema(description = "openapi.note.tags")
    val tags: List<TagVO>,
)