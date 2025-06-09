package com.juancarsg.reviews.backend.util;

import com.juancarsg.reviews.backend.entity.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtils {

    private UserUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static User getAuthenticatedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof User)) {
            throw new AccessDeniedException("Authenticated principal is not of type User.");
        }
        return (User) principal;
    }

 }
