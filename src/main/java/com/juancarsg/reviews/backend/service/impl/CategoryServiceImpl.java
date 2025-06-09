package com.juancarsg.reviews.backend.service.impl;

import com.juancarsg.reviews.backend.dto.PaginatedCriteria;
import com.juancarsg.reviews.backend.dto.PaginatedDto;
import com.juancarsg.reviews.backend.dto.SimpleDto;
import com.juancarsg.reviews.backend.entity.Category;
import com.juancarsg.reviews.backend.exception.CustomException;
import com.juancarsg.reviews.backend.repository.CategoryRepository;
import com.juancarsg.reviews.backend.service.CategoryService;
import com.juancarsg.reviews.backend.util.mapper.CategoryMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public ResponseEntity<PaginatedDto<SimpleDto>> getCategories(PaginatedCriteria criteria) {
        if (!criteria.getSortBy().equals("id") && !criteria.getSortBy().equals("name")) {
            throw new CustomException("Sorting param must be 'id' or 'name'", HttpStatus.BAD_REQUEST.value());
        }
        Sort sort = criteria.isAscending() ?
                Sort.by(Sort.Direction.ASC, criteria.getSortBy()) :
                Sort.by(Sort.Direction.DESC, criteria.getSortBy());
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getPageSize()).withSort(sort);
        Page<Category> categories = categoryRepository.findAll(pageable);
        return categories.getContent().isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(categoryMapper.convertEntityPageToPaginatedDto(categories));
    }

    public ResponseEntity<SimpleDto> createCategory(String categoryName) {
        Category category = categoryRepository.save(new Category(categoryName));
        return ResponseEntity.ok(categoryMapper.convertEntityToSimpleDto(category));
    }

    public ResponseEntity<Void> deleteCategoryById(Long id) {
        if (!categoryRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        categoryRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
