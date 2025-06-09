package com.juancarsg.reviews.backend.dto;

import lombok.Data;

@Data
public class PaginatedCriteria {
    private int page = 0;
    private int pageSize = 10;
    private String sortBy = "id";
    private boolean ascending = true;
}