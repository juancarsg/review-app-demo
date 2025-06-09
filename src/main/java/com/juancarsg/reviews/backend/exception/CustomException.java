package com.juancarsg.reviews.backend.exception;

import lombok.Data;

@Data
public class CustomException extends RuntimeException {

    private final String message;
    private final int status;

    public CustomException(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
