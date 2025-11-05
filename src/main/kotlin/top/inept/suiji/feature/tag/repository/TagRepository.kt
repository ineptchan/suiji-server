package top.inept.suiji.feature.tag.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import top.inept.suiji.feature.tag.domain.entity.Tag

@Repository
interface TagRepository : JpaRepository<Tag, Long> {
    fun findByNameContains(name: String, pageable: Pageable): Page<Tag>
}