package top.inept.suiji.feature.note.repository

import org.springframework.data.jpa.domain.Specification
import top.inept.suiji.feature.category.domain.entity.Category_
import top.inept.suiji.feature.note.domian.entity.Note
import top.inept.suiji.feature.note.domian.entity.Note_
import top.inept.suiji.feature.tag.domain.entity.Tag_
import java.util.*

object NoteSpecifications {
    fun titleContainsIgnoreCase(keyword: String?): Specification<Note>? {
        return keyword?.takeIf { it.isNotBlank() }?.let {
            Specification { root, _, cb ->
                cb.like(cb.lower(root.get(Note_.title)), "%${it.lowercase()}%")
            }
        }
    }

    fun publicIdEquals(uuid: UUID?): Specification<Note>? {
        return uuid?.let {
            Specification<Note> { root, _, cb ->
                cb.equal(root.get(Note_.publicId), it)
            }
        }
    }

    fun categoryEquals(categoryId: Long?): Specification<Note>? {
        return categoryId?.let {
            Specification<Note> { root, _, cb ->
                cb.equal(root.get(Note_.category).get(Category_.id), it)
            }
        }
    }

    fun hasTagId(tags: List<Long>?): Specification<Note>? {
        return tags.takeIf { it != null && it.isNotEmpty() }?.let {
            Specification { root, query, cb ->
                query?.distinct(true)
                val tagsJoin = root.join(Note_.tags)
                tagsJoin.get(Tag_.id).`in`(it)
            }
        }
    }
}