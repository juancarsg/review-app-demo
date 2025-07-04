package com.juancarsg.reviews.backend.repository;

import com.juancarsg.reviews.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByName(String name);
    boolean existsByEmail(String email);
    Optional<User> getUserByName(String name);
}

