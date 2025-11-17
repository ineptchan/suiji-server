package top.inept.suiji.feature.tag.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import top.inept.blog.extensions.toPageResponse
import top.inept.blog.extensions.toResponseEntity
import top.inept.suiji.base.PageResponse
import top.inept.suiji.feature.tag.domain.convert.toTagVO
import top.inept.suiji.feature.tag.domain.dto.CreateTagDTO
import top.inept.suiji.feature.tag.domain.dto.QueryTagDTO
import top.inept.suiji.feature.tag.domain.dto.UpdateTagDTO
import top.inept.suiji.feature.tag.domain.vo.TagVO
import top.inept.suiji.feature.tag.service.TagService

@Tag(name = "标签")
@RestController
@RequestMapping("/tag")
@Validated
class TagController(private val tagService: TagService) {
    @Operation(summary = "获取标签列表")
    @GetMapping
    fun getTag(@Valid dto: QueryTagDTO): ResponseEntity<PageResponse<TagVO>> {
        val tagPage = tagService.getTags(dto)
        return tagPage.toPageResponse { it.toTagVO() }.toResponseEntity()
    }

    @Operation(summary = "按Id获取标签")
    @GetMapping("/{id}")
    fun getTagById(@PathVariable id: Long): ResponseEntity<TagVO> {
        val tag = tagService.getTagById(id)
        return ResponseEntity.ok(tag.toTagVO())
    }

    @Operation(summary = "创建标签")
    @PostMapping
    fun createTag(@Valid @RequestBody dto: CreateTagDTO): ResponseEntity<TagVO> {
        return ResponseEntity.ok(tagService.createTag(dto).toTagVO())
    }

    @Operation(summary = "更新标签")
    @PutMapping("/{id}")
    fun updateTag(@PathVariable id: Long, @Valid @RequestBody dto: UpdateTagDTO): ResponseEntity<TagVO> {
        return ResponseEntity.ok(tagService.updateTag(id, dto).toTagVO())
    }

    @Operation(summary = "删除标签")
    @DeleteMapping("/{id}")
    fun deleteTag(@PathVariable id: Long): ResponseEntity<Boolean> {
        tagService.deleteTag(id)
        return ResponseEntity.ok(true)
    }
}