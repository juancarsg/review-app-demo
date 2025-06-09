package com.juancarsg.reviews.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedDto<T> {
    private List<T> data = new ArrayList<>();
    private int pageSize;
    private int page;
    private int totalPages;
    private long totalElements;
}
