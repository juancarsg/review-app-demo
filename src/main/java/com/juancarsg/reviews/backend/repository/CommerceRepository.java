package com.juancarsg.reviews.backend.repository;

import com.juancarsg.reviews.backend.entity.Commerce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CommerceRepository extends JpaRepository<Commerce, Long>, JpaSpecificationExecutor<Commerce> {
}
