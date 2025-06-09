package com.juancarsg.reviews.backend.util.mapper;

import com.juancarsg.reviews.backend.dto.PaginatedDto;
import com.juancarsg.reviews.backend.dto.commerce.CommerceCriteriaDto;
import com.juancarsg.reviews.backend.dto.review.ReviewCriteriaDto;
import com.juancarsg.reviews.backend.dto.review.ReviewRequestDto;
import com.juancarsg.reviews.backend.dto.review.ReviewResponseDto;
import com.juancarsg.reviews.backend.entity.Commerce;
import com.juancarsg.reviews.backend.entity.Review;
import com.juancarsg.reviews.backend.repository.CommerceRepository;
import com.juancarsg.reviews.backend.util.UserUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReviewMapper {

    private final CommerceMapper commerceMapper;
    private final UserMapper userMapper;
    private final CommerceRepository commerceRepository;

    public ReviewMapper(CommerceMapper commerceMapper, UserMapper userMapper, CommerceRepository commerceRepository) {
        this.commerceMapper = commerceMapper;
        this.userMapper = userMapper;
        this.commerceRepository = commerceRepository;
    }

    public PaginatedDto<ReviewResponseDto> convertEntityPageToPaginatedDto(Page<Review> entityPage, ReviewCriteriaDto criteria) {
        return new PaginatedDto<>(
                convertEntityListToDtoList(entityPage.getContent(), criteria),
                entityPage.getSize(),
                entityPage.getNumber(),
                entityPage.getTotalPages(),
                entityPage.getTotalElements()
        );
    }

    public List<ReviewResponseDto> convertEntityListToDtoList(List<Review> entityList, ReviewCriteriaDto criteria) {
        return entityList.stream().map(entity -> this.convertEntityToDto(entity, criteria)).toList();
    }

    public ReviewResponseDto convertEntityToDto(Review entity, ReviewCriteriaDto criteria) {
        ReviewResponseDto dto = new ReviewResponseDto(
                entity.getId(),
                entity.getContent(),
                entity.getRating(),
                entity.getCreationDate()
        );
        if (criteria.isIncludeCommerce()) {
            CommerceCriteriaDto commerceCriteria = new CommerceCriteriaDto();
            if (criteria.isIncludeCommerceCategories()) {
                commerceCriteria.setIncludeCategories(true);
            }
            if (criteria.isIncludeCommerceSchedules()) {
                commerceCriteria.setIncludeSchedules(true);
            }
            dto.setCommerce(commerceMapper.convertEntityToDto(entity.getCommerce(), commerceCriteria));
        }
        if (criteria.isIncludeUser()){
            dto.setUser(userMapper.convertEntityToDto(entity.getUser()));
        }
        return dto;
    }

    public Review updateEntityWithDto(Review entity, ReviewRequestDto dto) {
        if (dto.getContent() != null && !dto.getContent().isBlank()) {
            entity.setContent(dto.getContent());
        }
        if (dto.getRating() != null) {
            entity.setRating(dto.getRating());
        }
        return entity;
    }

    public Review convertDtoToEntity(ReviewRequestDto dto) {
        Commerce commerce = commerceRepository.findById(dto.getCommerceId())
                .orElseThrow(() -> new EntityNotFoundException("Commerce with id " + dto.getCommerceId() + " not found"));
        return new Review(dto.getContent(), dto.getRating(), UserUtils.getAuthenticatedUser(), commerce);
    }

}
