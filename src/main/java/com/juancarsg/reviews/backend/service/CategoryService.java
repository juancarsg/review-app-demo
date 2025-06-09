package com.juancarsg.reviews.backend.service;

import com.juancarsg.reviews.backend.dto.PaginatedCriteria;
import com.juancarsg.reviews.backend.dto.PaginatedDto;
import com.juancarsg.reviews.backend.dto.SimpleDto;
import org.springframework.http.ResponseEntity;

public interface CategoryService {
    ResponseEntity<PaginatedDto<SimpleDto>> getCategories(PaginatedCriteria criteria);
    ResponseEntity<SimpleDto> createCategory(String categoryName);
    ResponseEntity<Void> deleteCategoryById(Long id);
}

