package com.juancarsg.reviews.backend.dto.commerce;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommerceRequestDto {
    @NotEmpty(groups = ValidationGroups.Create.class)
    private String name;
    @NotEmpty(groups = ValidationGroups.Create.class)
    private String address;
    @NotEmpty(groups = ValidationGroups.Create.class)
    @Pattern(regexp = "^(\\+44|0)7\\d{9}$",
            message = "This number doesn't belong to the UK",
            groups = { ValidationGroups.Create.class, ValidationGroups.Update.class })
    private String phone;
    @NotEmpty(groups = ValidationGroups.Create.class)
    private String description;
    @NotEmpty(groups = ValidationGroups.Create.class)
    private Set<Long> categories;
    @NotEmpty(groups = ValidationGroups.Create.class)
    private Set<Long> schedules;

    public interface ValidationGroups {
        interface Create {
        }
        interface Update {
        }
    }
}
