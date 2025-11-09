package top.inept.suiji.feature.note.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import top.inept.blog.extensions.toApiResponse
import top.inept.blog.extensions.toPageResponse
import top.inept.suiji.base.ApiResponse
import top.inept.suiji.base.PageResponse
import top.inept.suiji.feature.note.domian.convert.toNoteVO
import top.inept.suiji.feature.note.domian.dto.CreateNoteDTO
import top.inept.suiji.feature.note.domian.dto.QueryNoteDTO
import top.inept.suiji.feature.note.domian.dto.UpdateNoteDTO
import top.inept.suiji.feature.note.domian.vo.NoteVO
import top.inept.suiji.feature.note.service.NoteService


@Tag(name = "笔记")
@RestController
@RequestMapping("/note")
@Validated
class NoteController(private val noteService: NoteService) {
    @Operation(summary = "获取笔记列表")
    @GetMapping
    fun getNote(@Valid dto: QueryNoteDTO): ApiResponse<PageResponse<NoteVO>> {
        val notePage = noteService.getNotes(dto)
        return notePage.toPageResponse().toApiResponse()
    }

    @Operation(summary = "按Id获取笔记")
    @GetMapping("/{id}")
    fun getNoteById(@PathVariable id: Long): ApiResponse<NoteVO> {
        val note = noteService.getNoteById(id)
        return ApiResponse.success(note)
    }

    @Operation(summary = "按PublicId获取笔记")
    @GetMapping("/publicid/{id}")
    fun getNoteByPublicId(@PathVariable id: String): ApiResponse<NoteVO> {
        val note = noteService.getNoteByPublicId(id)
        return ApiResponse.success(note)
    }

    @Operation(summary = "创建笔记")
    @PostMapping
    fun createNote(@Valid @RequestBody dto: CreateNoteDTO): ApiResponse<NoteVO> {
        return ApiResponse.success(noteService.createNote(dto))
    }

    @Operation(summary = "更新笔记")
    @PutMapping("/{id}")
    fun updateNote(@PathVariable id: Long, @Valid @RequestBody dto: UpdateNoteDTO): ApiResponse<NoteVO> {
        return ApiResponse.success(noteService.updateNote(id, dto))
    }

    @Operation(summary = "删除笔记")
    @DeleteMapping("/{id}")
    fun deleteNote(@PathVariable id: Long): ApiResponse<Boolean> {
        noteService.deleteNote(id)
        return ApiResponse.success(true)
    }
}