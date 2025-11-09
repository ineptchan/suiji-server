package top.inept.suiji.feature.note.service

import org.springframework.data.domain.Page
import top.inept.suiji.feature.note.domian.dto.CreateNoteDTO
import top.inept.suiji.feature.note.domian.dto.QueryNoteDTO
import top.inept.suiji.feature.note.domian.dto.UpdateNoteDTO
import top.inept.suiji.feature.note.domian.vo.NoteVO

interface NoteService {
    fun getNotes(dto: QueryNoteDTO): Page<NoteVO>
    fun getNoteById(id: Long): NoteVO
    fun getNoteByPublicId(publicId: String): NoteVO
    fun createNote(dto: CreateNoteDTO): NoteVO
    fun deleteNote(id: Long): Boolean
    fun deleteNoteByPublicId(publicId: String)
    fun updateNote(id: Long, dto: UpdateNoteDTO): NoteVO
}