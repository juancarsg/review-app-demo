package com.juancarsg.reviews.backend.exception;

import lombok.Data;

@Data
public class CustomExceptionDto {
    private String url;
    private String type;
    private String message;
    private int status;
}
