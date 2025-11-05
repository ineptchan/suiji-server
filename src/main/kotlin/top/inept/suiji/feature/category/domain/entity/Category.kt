package top.inept.suiji.feature.category.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "category")
open class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @Column(name = "name", nullable = false, unique = true, length = 36)
    open var name: String = ""
}