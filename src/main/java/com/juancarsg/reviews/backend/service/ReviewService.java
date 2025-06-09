package com.juancarsg.reviews.backend.service;

import com.juancarsg.reviews.backend.dto.PaginatedDto;
import com.juancarsg.reviews.backend.dto.review.ReviewCriteriaDto;
import com.juancarsg.reviews.backend.dto.review.ReviewRequestDto;
import com.juancarsg.reviews.backend.dto.review.ReviewResponseDto;
import org.springframework.http.ResponseEntity;

public interface ReviewService {
    ResponseEntity<PaginatedDto<ReviewResponseDto>> getReviews(ReviewCriteriaDto criteria);
    ResponseEntity<ReviewResponseDto> createReview(ReviewRequestDto creationDto);
    ResponseEntity<ReviewResponseDto> updateReview(Long id, ReviewRequestDto updateDto);
    ResponseEntity<Void> deleteReviewById(Long id);
}


