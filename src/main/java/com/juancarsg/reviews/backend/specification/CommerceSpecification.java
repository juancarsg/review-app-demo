package com.juancarsg.reviews.backend.specification;

import com.juancarsg.reviews.backend.dto.commerce.CommerceCriteriaDto;
import com.juancarsg.reviews.backend.entity.Commerce;
import com.juancarsg.reviews.backend.exception.CustomException;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.query.sqm.PathElementException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class CommerceSpecification {

    private CommerceSpecification() {
        throw new IllegalStateException("Utility class");
    }

    public static Specification<Commerce> getCommerceSpecification(CommerceCriteriaDto criteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getIds() != null && !criteria.getIds().isEmpty()) {
                predicates.add(root.get("id").in(criteria.getIds()));
            }

            if (criteria.getNames() != null && !criteria.getNames().isEmpty()) {
                List<Predicate> namePredicates = new ArrayList<>();
                for (String name : criteria.getNames()) {
                    namePredicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
                }
                predicates.add(criteriaBuilder.or(namePredicates.toArray(new Predicate[0])));
            }

            if (criteria.getPhones() != null && !criteria.getPhones().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("phone"), "%" + criteria.getPhones() + "%"));
            }

            if (criteria.getCategoryIds() != null && !criteria.getCategoryIds().isEmpty()) {
                predicates.add(root.join("commerceCategories").get("category").get("id").in(criteria.getCategoryIds()));
            }

            if ("rating".equals(criteria.getSortBy())) {
                Join<Object, Object> commerceStatJoin = root.join("commerceStat");
                query.orderBy(criteria.isAscending()
                        ? criteriaBuilder.asc(commerceStatJoin.get("avgRating"))
                        : criteriaBuilder.desc(commerceStatJoin.get("avgRating")));
            } else if ("reviews".equals(criteria.getSortBy())) {
                Join<Object, Object> commerceStatJoin = root.join("commerceStat");
                query.orderBy(criteria.isAscending()
                        ? criteriaBuilder.asc(commerceStatJoin.get("countReviews"))
                        : criteriaBuilder.desc(commerceStatJoin.get("countReviews")));
            } else {
                try {
                    query.orderBy(criteria.isAscending() ?
                            criteriaBuilder.asc(root.get(criteria.getSortBy())) :
                            criteriaBuilder.desc(root.get(criteria.getSortBy())));
                } catch (PathElementException pee) {
                    throw new CustomException(pee.getMessage(), HttpStatus.BAD_REQUEST.value());
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}