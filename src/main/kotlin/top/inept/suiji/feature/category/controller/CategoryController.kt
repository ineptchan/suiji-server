package top.inept.suiji.feature.category.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import top.inept.blog.extensions.toPageResponse
import top.inept.blog.extensions.toResponseEntity
import top.inept.suiji.base.PageResponse
import top.inept.suiji.feature.category.domain.convert.toCategoryVO
import top.inept.suiji.feature.category.domain.dto.CreateCategoryDTO
import top.inept.suiji.feature.category.domain.dto.QueryCategoryDTO
import top.inept.suiji.feature.category.domain.dto.UpdateCategoryDTO
import top.inept.suiji.feature.category.domain.vo.CategoryVO
import top.inept.suiji.feature.category.service.CategoryService

@Tag(name = "类别")
@RestController
@RequestMapping("/category")
@Validated
class CategoryController(private val categoryService: CategoryService) {
    @Operation(summary = "获取类别列表")
    @GetMapping
    fun getCategory(@Valid dto: QueryCategoryDTO): ResponseEntity<PageResponse<CategoryVO>> {
        val categoryPage = categoryService.getCategory(dto)
        return categoryPage.toPageResponse { it.toCategoryVO() }.toResponseEntity()
    }

    @Operation(summary = "按Id获取类别")
    @GetMapping("/{id}")
    fun getCategoryById(@PathVariable id: Long): ResponseEntity<CategoryVO> {
        val category = categoryService.getCategoryById(id)
        return ResponseEntity.ok(category.toCategoryVO())
    }

    @Operation(summary = "创建类别")
    @PostMapping
    fun createCategory(@Valid @RequestBody dto: CreateCategoryDTO): ResponseEntity<CategoryVO> {
        return ResponseEntity.ok(categoryService.createCategory(dto).toCategoryVO())
    }

    @Operation(summary = "更新类别")
    @PutMapping("/{id}")
    fun updateCategory(@PathVariable id: Long, @Valid @RequestBody dto: UpdateCategoryDTO): ResponseEntity<CategoryVO> {
        return ResponseEntity.ok(categoryService.updateCategory(id, dto).toCategoryVO())
    }

    @Operation(summary = "删除类别")
    @DeleteMapping("/{id}")
    fun deleteCategory(@PathVariable id: Long): ResponseEntity<Boolean> {
        categoryService.deleteCategory(id)
        return ResponseEntity.ok(true)
    }
}