package top.inept.suiji.feature.note.domian.entity

import jakarta.persistence.*
import top.inept.suiji.feature.category.domain.entity.Category
import top.inept.suiji.feature.tag.domain.entity.Tag
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "note")
open class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @Column(name = "public_id", nullable = false, unique = true)
    open var publicId: UUID? = null

    @Column(name = "title", nullable = false, length = 32)
    open var title: String = ""

    @Column(name = "content", nullable = false)
    open var content: String = ""

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    open var category: Category? = null

    @ManyToMany
    @JoinTable(
        name = "note_tag",
        joinColumns = [JoinColumn(name = "note_id")],
        inverseJoinColumns = [JoinColumn(name = "tag_id")]
    )
    @OrderBy("id ASC")
    open var tags: MutableSet<Tag> = mutableSetOf()

    @Column(name = "created_at", nullable = false)
    open var createdAt: LocalDateTime? = null

    @Column(name = "update_at")
    open var updateAt: LocalDateTime? = null
}