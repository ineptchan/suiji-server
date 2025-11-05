package top.inept.suiji.feature.category.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import top.inept.blog.extensions.toApiResponse
import top.inept.blog.extensions.toPageResponse
import top.inept.suiji.base.ApiResponse
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
    fun getCategory(@Valid queryCategoryDTO: QueryCategoryDTO): ApiResponse<PageResponse<CategoryVO>> {
        val categoryPage = categoryService.getCategory(queryCategoryDTO)
        return categoryPage.toPageResponse { it.toCategoryVO() }.toApiResponse()
    }

    @Operation(summary = "按Id获取类别")
    @GetMapping("/{id}")
    fun getCategoryById(@PathVariable id: Long): ApiResponse<CategoryVO> {
        val category = categoryService.getCategoryById(id)
        return ApiResponse.success(category.toCategoryVO())
    }

    @Operation(summary = "创建类别")
    @PostMapping
    fun createCategory(@Valid @RequestBody dto: CreateCategoryDTO): ApiResponse<CategoryVO> {
        return ApiResponse.success(categoryService.createCategory(dto).toCategoryVO())
    }

    @Operation(summary = "更新类别")
    @PutMapping
    fun updateCategory(@Valid @RequestBody dto: UpdateCategoryDTO): ApiResponse<CategoryVO> {
        return ApiResponse.success(categoryService.updateCategory(dto).toCategoryVO())
    }

    @Operation(summary = "删除类别")
    @DeleteMapping("/{id}")
    fun deleteCategory(@PathVariable id: Long): ApiResponse<Boolean> {
        categoryService.deleteCategory(id)
        return ApiResponse.success(true)
    }
}