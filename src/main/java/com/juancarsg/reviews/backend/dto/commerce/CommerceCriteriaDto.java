package com.juancarsg.reviews.backend.dto.commerce;

import com.juancarsg.reviews.backend.dto.PaginatedCriteria;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CommerceCriteriaDto extends PaginatedCriteria {
    private List<Long> ids;
    private List<String> names;
    private List<String> phones;
    private List<Long> categoryIds;
    private boolean includeCategories = false;
    private boolean includeSchedules = false;
}
