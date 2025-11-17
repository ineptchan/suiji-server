package top.inept.suiji.feature.note.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import top.inept.blog.extensions.toPageResponse
import top.inept.blog.extensions.toResponseEntity
import top.inept.suiji.base.PageResponse
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
    fun getNote(@Valid dto: QueryNoteDTO): ResponseEntity<PageResponse<NoteVO>> {
        val notePage = noteService.getNotes(dto)
        return notePage.toPageResponse().toResponseEntity()
    }

    @Operation(summary = "按Id获取笔记")
    @GetMapping("/{id}")
    fun getNoteById(@PathVariable id: Long): ResponseEntity<NoteVO> {
        val note = noteService.getNoteById(id)
        return ResponseEntity.ok(note)
    }

    @Operation(summary = "按PublicId获取笔记")
    @GetMapping("/publicid/{id}")
    fun getNoteByPublicId(@PathVariable id: String): ResponseEntity<NoteVO> {
        val note = noteService.getNoteByPublicId(id)
        return ResponseEntity.ok(note)
    }

    @Operation(summary = "创建笔记")
    @PostMapping
    fun createNote(@Valid @RequestBody dto: CreateNoteDTO): ResponseEntity<NoteVO> {
        return ResponseEntity.ok(noteService.createNote(dto))
    }

    @Operation(summary = "更新笔记")
    @PutMapping("/{id}")
    fun updateNote(@PathVariable id: Long, @Valid @RequestBody dto: UpdateNoteDTO): ResponseEntity<NoteVO> {
        return ResponseEntity.ok(noteService.updateNote(id, dto))
    }

    @Operation(summary = "删除笔记")
    @DeleteMapping("/{id}")
    fun deleteNote(@PathVariable id: Long): ResponseEntity<Boolean> {
        noteService.deleteNote(id)
        return ResponseEntity.ok(true)
    }
}