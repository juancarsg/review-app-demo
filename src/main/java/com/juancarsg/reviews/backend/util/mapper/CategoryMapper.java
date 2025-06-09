package com.juancarsg.reviews.backend.util.mapper;

import com.juancarsg.reviews.backend.dto.PaginatedDto;
import com.juancarsg.reviews.backend.dto.SimpleDto;
import com.juancarsg.reviews.backend.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryMapper {

    public PaginatedDto<SimpleDto> convertEntityPageToPaginatedDto(Page<Category> entityPage) {
        return new PaginatedDto<>(
                convertEntityListToSimpleDtoList(entityPage.getContent()),
                entityPage.getSize(),
                entityPage.getNumber(),
                entityPage.getTotalPages(),
                entityPage.getTotalElements()
        );
    }

    public List<SimpleDto> convertEntityListToSimpleDtoList(List<Category> entityList) {
        return entityList.stream().map(this::convertEntityToSimpleDto).toList();
    }

    public SimpleDto convertEntityToSimpleDto(Category entity) {
        return entity != null ?
                new SimpleDto(entity.getId(), entity.getName()) : null;
    }

}
