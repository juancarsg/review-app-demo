package com.juancarsg.reviews.backend.util;

public class Cons {

    private Cons() {
        throw new IllegalStateException("Utility class");
    }

    public static final String USER_ADMIN = "admin";
    public static final String USER_MODERATOR = "moderator";
    public static final String USER_OWNER = "owner";
    public static final String USER = "user";

}