package com.juancarsg.reviews.backend.service.impl;

import com.juancarsg.reviews.backend.dto.PaginatedDto;
import com.juancarsg.reviews.backend.dto.review.ReviewCriteriaDto;
import com.juancarsg.reviews.backend.dto.review.ReviewRequestDto;
import com.juancarsg.reviews.backend.dto.review.ReviewResponseDto;
import com.juancarsg.reviews.backend.entity.Review;
import com.juancarsg.reviews.backend.repository.CommerceRepository;
import com.juancarsg.reviews.backend.repository.ReviewRepository;
import com.juancarsg.reviews.backend.service.ReviewService;
import com.juancarsg.reviews.backend.specification.ReviewSpecification;
import com.juancarsg.reviews.backend.util.UserUtils;
import com.juancarsg.reviews.backend.util.mapper.ReviewMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final CommerceRepository commerceRepository;
    private final ReviewMapper reviewMapper;

    public ReviewServiceImpl(ReviewRepository reviewRepository, CommerceRepository commerceRepository, ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.commerceRepository = commerceRepository;
        this.reviewMapper = reviewMapper;
    }

    public ResponseEntity<PaginatedDto<ReviewResponseDto>> getReviews(ReviewCriteriaDto criteria) {
        if (criteria.getCommerceId() != null && !commerceRepository.existsById(criteria.getCommerceId())) {
            throw new EntityNotFoundException("Commerce with id " + criteria.getCommerceId() + " is not found.");
        }
        Specification<Review> specification = ReviewSpecification.getReviewSpecification(criteria);
        Sort sort = criteria.isAscending() ? Sort.by(criteria.getSortBy()).ascending() : Sort.by(criteria.getSortBy()).descending();
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getPageSize()).withSort(sort);
        Page<Review> reviews = reviewRepository.findAll(specification, pageable);
        if (reviews.getContent().isEmpty()) {
            return ResponseEntity.ok().body(new PaginatedDto<>());
        }
        return ResponseEntity.ok(reviewMapper.convertEntityPageToPaginatedDto(reviews, criteria));
    }

    public ResponseEntity<ReviewResponseDto> createReview(ReviewRequestDto creationDto) {
        Review review = reviewRepository.save(reviewMapper.convertDtoToEntity(creationDto));
        ReviewCriteriaDto criteria = new ReviewCriteriaDto();
        criteria.setIncludeCommerce(true);
        return ResponseEntity.ok(reviewMapper.convertEntityToDto(review, criteria));
    }

    public ResponseEntity<ReviewResponseDto> updateReview(Long id, ReviewRequestDto updateDto) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review with id " + id + " not found."));
        if (!review.getUser().equals(UserUtils.getAuthenticatedUser())) {
            throw new AuthorizationDeniedException("You don't have permission to perform this action.");
        }
        review = reviewMapper.updateEntityWithDto(review, updateDto);
        review = reviewRepository.save(review);
        ReviewCriteriaDto criteria = new ReviewCriteriaDto();
        criteria.setIncludeCommerce(true);
        return ResponseEntity.ok(reviewMapper.convertEntityToDto(review, criteria));
    }

    public ResponseEntity<Void> deleteReviewById(Long id) {
        Review review = reviewRepository.findById(id).orElse(null);
        if (review == null) {
            return ResponseEntity.notFound().build();
        }
        if (!review.getUser().equals(UserUtils.getAuthenticatedUser())) {
            throw new AuthorizationDeniedException("You don't have permission to perform this action.");
        }
        reviewRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
