package top.inept.suiji.feature.category.service.impl

import org.springframework.context.support.MessageSourceAccessor
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.Page
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import top.inept.suiji.exception.business.DuplicateException
import top.inept.suiji.exception.business.EntityNotFoundException
import top.inept.suiji.extensions.get
import top.inept.suiji.extensions.toPageRequest
import top.inept.suiji.feature.category.domain.convert.toCategory
import top.inept.suiji.feature.category.domain.dto.CreateCategoryDTO
import top.inept.suiji.feature.category.domain.dto.QueryCategoryDTO
import top.inept.suiji.feature.category.domain.dto.UpdateCategoryDTO
import top.inept.suiji.feature.category.domain.entity.Category
import top.inept.suiji.feature.category.repository.CategoryRepository
import top.inept.suiji.feature.category.service.CategoryService

@Service
class CategoryServiceImpl(
    private val messages: MessageSourceAccessor,
    private val categoryRepository: CategoryRepository
) : CategoryService {
    override fun getCategory(dto: QueryCategoryDTO): Page<Category> {
        val pageRequest = dto.toPageRequest()

        return if (dto.name.isEmpty()) {
            categoryRepository.findAll(pageRequest)
        } else {
            categoryRepository.findByNameContains(dto.name, pageRequest)
        }
    }

    override fun getCategoryById(id: Long): Category {
        val category = categoryRepository.findByIdOrNull(id)
        if (category == null) throw EntityNotFoundException(messages["message.category.not_found"])
        return category
    }

    override fun createCategory(dto: CreateCategoryDTO): Category {
        return try {
            categoryRepository.save(dto.toCategory())
        } catch (ex: DataIntegrityViolationException) {
            throw DuplicateException(messages["message.category.duplicate_name"])
        }
    }

    override fun deleteCategory(id: Long) {
        categoryRepository.deleteById(id)
    }

    override fun updateCategory(dto: UpdateCategoryDTO): Category {
        if (!categoryRepository.existsById(dto.id)) throw EntityNotFoundException(messages["message.category.not_found"])

        try {
            return categoryRepository.save(dto.toCategory())
        } catch (ex: DataIntegrityViolationException) {
            throw DuplicateException(messages["message.category.duplicate_name"])
        }
    }


}