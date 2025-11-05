package top.inept.suiji.feature.tag.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "tag")
open class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @Column(name = "name", nullable = false, unique = true, length = 36)
    open var name: String = ""
}