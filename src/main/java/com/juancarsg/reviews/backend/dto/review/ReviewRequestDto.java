package com.juancarsg.reviews.backend.dto.review;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class ReviewRequestDto {
    @NotEmpty(groups = ValidationGroups.Create.class)
    private String content;
    @NotNull(groups = ValidationGroups.Create.class)
    private Integer rating;
    @NotNull(groups = ValidationGroups.Create.class)
    private Long commerceId;
    private Set<String> imageUrls = new HashSet<>();

    public interface ValidationGroups {
        interface Create {
        }

        interface Update {
        }
    }

}
