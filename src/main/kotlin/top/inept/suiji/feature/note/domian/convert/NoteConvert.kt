package top.inept.suiji.feature.note.domian.convert

import top.inept.suiji.feature.category.domain.entity.Category
import top.inept.suiji.feature.category.domain.vo.CategoryVO
import top.inept.suiji.feature.note.domian.dto.CreateNoteDTO
import top.inept.suiji.feature.note.domian.entity.Note
import top.inept.suiji.feature.note.domian.vo.NoteVO
import top.inept.suiji.feature.tag.domain.entity.Tag
import top.inept.suiji.feature.tag.domain.vo.TagVO
import java.time.LocalDateTime
import java.util.*

fun CreateNoteDTO.toNote(category: Category, tags: MutableSet<Tag>) = Note().also {
    it.publicId = UUID.randomUUID()
    it.title = this.title
    it.content = this.content
    it.category = category
    it.tags = tags
    it.createdAt = LocalDateTime.now()
}

fun Note.toNoteVO(categoryVO: CategoryVO, tags: List<TagVO>): NoteVO {
    return NoteVO(
        id = this.id ?: throw IllegalStateException("Note id is null"),
        publicId = this.publicId.toString(),
        title = this.title,
        content = this.content,
        category = categoryVO,
        tags = tags
    )
}