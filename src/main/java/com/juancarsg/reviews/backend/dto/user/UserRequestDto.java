package com.juancarsg.reviews.backend.dto.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserRequestDto {
    @NotEmpty(groups = ValidationGroups.Create.class)
    private String name;
    @NotEmpty(groups = ValidationGroups.Create.class)
    private String email;
    @NotEmpty(groups = ValidationGroups.Create.class)
    private String password;

    public interface ValidationGroups {
        interface Create {}
        interface Update {}
    }

}
