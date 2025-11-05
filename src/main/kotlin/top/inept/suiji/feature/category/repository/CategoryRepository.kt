package top.inept.suiji.feature.category.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import top.inept.suiji.feature.category.domain.entity.Category

@Repository
interface CategoryRepository : JpaRepository<Category, Long> {
    fun findByNameContains(name: String, pageable: Pageable): Page<Category>
}