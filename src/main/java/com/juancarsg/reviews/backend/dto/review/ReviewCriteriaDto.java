package com.juancarsg.reviews.backend.dto.review;

import com.juancarsg.reviews.backend.dto.PaginatedCriteria;
import lombok.Data;

import java.util.List;

@Data
public class ReviewCriteriaDto extends PaginatedCriteria {
    private List<Long> ids;
    private List<Integer> ratings;
    private Long commerceId;
    private boolean includeUser = false;
    private boolean includeCommerce = false;
    private boolean includeCommerceCategories = false;
    private boolean includeCommerceSchedules = false;

}
