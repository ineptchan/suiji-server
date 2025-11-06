package top.inept.suiji.feature.tag.domain.convert

import top.inept.suiji.feature.tag.domain.dto.CreateTagDTO
import top.inept.suiji.feature.tag.domain.dto.UpdateTagDTO
import top.inept.suiji.feature.tag.domain.entity.Tag
import top.inept.suiji.feature.tag.domain.vo.TagVO


fun CreateTagDTO.toTag() = Tag().also {
    it.name = this.name
}

fun UpdateTagDTO.toTag(id: Long) = Tag().also {
    it.id = id
    it.name = this.name
}

fun Tag.toTagVO() = TagVO(
    id = this.id ?: 0L,
    name = this.name
)