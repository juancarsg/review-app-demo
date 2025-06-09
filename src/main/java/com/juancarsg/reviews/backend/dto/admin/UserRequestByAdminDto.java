package com.juancarsg.reviews.backend.dto.admin;

import com.juancarsg.reviews.backend.dto.user.UserRequestDto;
import com.juancarsg.reviews.backend.util.Cons;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRequestByAdminDto extends UserRequestDto {
    @Pattern(regexp = Cons.USER_ADMIN + "|" + Cons.USER_MODERATOR + "|" + Cons.USER_OWNER + "|" + Cons.USER,
             message = "The role value is not valid.")
    private String role;
}
