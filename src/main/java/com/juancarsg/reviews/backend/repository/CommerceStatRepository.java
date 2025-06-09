package com.juancarsg.reviews.backend.repository;

import com.juancarsg.reviews.backend.entity.CommerceStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommerceStatRepository extends JpaRepository<CommerceStat, Long> {
}
