package com.juancarsg.reviews.backend.dto.review;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.juancarsg.reviews.backend.dto.commerce.CommerceResponseDto;
import com.juancarsg.reviews.backend.dto.user.UserResponseDto;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ReviewResponseDto {
    private Long id;
    private String content;
    private int rating;
    private LocalDate creationDate;
    private UserResponseDto user;
    private CommerceResponseDto commerce;

    public ReviewResponseDto(Long id, String content, int rating, LocalDate creationDate) {
        this.id = id;
        this.content = content;
        this.rating = rating;
        this.creationDate = creationDate;
    }

}
