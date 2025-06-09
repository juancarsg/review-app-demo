package com.juancarsg.reviews.backend.controller;

import com.juancarsg.reviews.backend.dto.PaginatedCriteria;
import com.juancarsg.reviews.backend.dto.PaginatedDto;
import com.juancarsg.reviews.backend.dto.SimpleDto;
import com.juancarsg.reviews.backend.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.juancarsg.reviews.backend.controller.CategoryController.API_PATH_CATEGORIES;

@RestController
@RequestMapping(API_PATH_CATEGORIES)
public class CategoryController {

    public static final String API_PATH_CATEGORIES = "/api/v1/categories";

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<PaginatedDto<SimpleDto>> getCategories(@ModelAttribute PaginatedCriteria criteria) {
        return categoryService.getCategories(criteria);
    }

    @PostMapping
    public ResponseEntity<SimpleDto> createCategory(@RequestParam String categoryName) {
        return categoryService.createCategory(categoryName);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCategory(@RequestParam Long categoryId) {
        return categoryService.deleteCategoryById(categoryId);
    }

}
