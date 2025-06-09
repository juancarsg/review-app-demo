package com.juancarsg.reviews.backend.controller;

import com.juancarsg.reviews.backend.dto.PaginatedDto;
import com.juancarsg.reviews.backend.dto.review.ReviewCriteriaDto;
import com.juancarsg.reviews.backend.dto.review.ReviewRequestDto;
import com.juancarsg.reviews.backend.dto.review.ReviewResponseDto;
import com.juancarsg.reviews.backend.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.juancarsg.reviews.backend.controller.ReviewController.API_PATH_REVIEWS;

@RestController
@RequestMapping(API_PATH_REVIEWS)
public class ReviewController {

    public static final String API_PATH_REVIEWS = "/api/v1/reviews";

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<PaginatedDto<ReviewResponseDto>> getReviews(@ModelAttribute ReviewCriteriaDto criteria) {
        return reviewService.getReviews(criteria);
    }

    @PostMapping
    public ResponseEntity<ReviewResponseDto> createReview(@Validated(ReviewRequestDto.ValidationGroups.Create.class) @RequestBody ReviewRequestDto requestDto) {
        return reviewService.createReview(requestDto);
    }

    @PatchMapping
    public ResponseEntity<ReviewResponseDto> updateReview(@RequestParam Long id,
                                                          @Validated(ReviewRequestDto.ValidationGroups.Update.class)
                                                              @RequestBody ReviewRequestDto requestDto) {
        return reviewService.updateReview(id, requestDto);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteReviewById(@RequestParam Long id) {
        return reviewService.deleteReviewById(id);
    }

}
