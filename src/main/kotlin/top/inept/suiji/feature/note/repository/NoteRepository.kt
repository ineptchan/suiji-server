package top.inept.suiji.feature.note.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import top.inept.suiji.feature.note.domian.entity.Note
import java.util.*

@Repository
interface NoteRepository : JpaRepository<Note, Long>, JpaSpecificationExecutor<Note> {
    fun findByPublicId(publicId: UUID): Note?
    fun deleteByPublicId(publicId: UUID)
    fun existsByPublicId(publicId: UUID): Boolean
}