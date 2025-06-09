package com.juancarsg.reviews.backend.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    private String role;
    private LocalDate registrationDate;
}
