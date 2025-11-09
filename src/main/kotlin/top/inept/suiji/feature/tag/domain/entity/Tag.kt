package top.inept.suiji.feature.tag.domain.entity

import jakarta.persistence.*
import top.inept.suiji.feature.note.domian.entity.Note

@Entity
@Table(name = "tag")
open class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @Column(name = "name", nullable = false, unique = true, length = 32)
    open var name: String = ""

    @ManyToMany(mappedBy = "tags")
    open var notes: MutableSet<Note> = mutableSetOf()
}