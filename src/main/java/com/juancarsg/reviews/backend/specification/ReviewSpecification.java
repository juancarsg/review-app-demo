package com.juancarsg.reviews.backend.specification;

import com.juancarsg.reviews.backend.dto.review.ReviewCriteriaDto;
import com.juancarsg.reviews.backend.entity.Review;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ReviewSpecification {

    private ReviewSpecification() {
        throw new IllegalStateException("Utility class");
    }

    public static Specification<Review> getReviewSpecification(ReviewCriteriaDto criteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getIds() != null && !criteria.getIds().isEmpty()) {
                predicates.add(root.get("id").in(criteria.getIds()));
            }

            if (criteria.getRatings() != null && !criteria.getRatings().isEmpty()) {
                predicates.add(root.get("rating").in(criteria.getRatings()));
            }

            if (criteria.getCommerceId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("commerce").get("id"), criteria.getCommerceId()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}