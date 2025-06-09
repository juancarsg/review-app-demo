package com.juancarsg.reviews.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimpleDto {
    private Long id;
    private String description;
}
