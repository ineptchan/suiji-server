package top.inept.suiji.feature.tag.service

import org.springframework.data.domain.Page
import top.inept.suiji.feature.tag.domain.dto.CreateTagDTO
import top.inept.suiji.feature.tag.domain.dto.QueryTagDTO
import top.inept.suiji.feature.tag.domain.dto.UpdateTagDTO

import top.inept.suiji.feature.tag.domain.entity.Tag

interface TagService {
    fun getTag(dto: QueryTagDTO): Page<Tag>
    fun getTagById(id: Long): Tag
    fun createTag(dto: CreateTagDTO): Tag
    fun deleteTag(id: Long)
    fun updateTag(dto: UpdateTagDTO): Tag
}