package com.juancarsg.reviews.backend.repository;

import com.juancarsg.reviews.backend.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, JpaSpecificationExecutor<Review> {

    @Query("SELECT COALESCE(AVG(r.rating), 0) FROM Review r WHERE r.commerce.id = :commerceId")
    Double avgRatingByCommerceId(@Param("commerceId") Long commerceId);

    Long countByCommerceId(Long commerceId);

    @Modifying
    @Query("DELETE FROM Review r WHERE r.commerce.id = :commerceId")
    void deleteByCommerceId(@Param("commerceId") Long commerceId);
}
