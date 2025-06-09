package com.juancarsg.reviews.backend.util.mapper;

import com.juancarsg.reviews.backend.dto.admin.UserRequestByAdminDto;
import com.juancarsg.reviews.backend.dto.user.UserRequestDto;
import com.juancarsg.reviews.backend.dto.user.UserResponseDto;
import com.juancarsg.reviews.backend.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDto convertEntityToDto(User entity) {
        return entity != null ?
                new UserResponseDto(entity.getId(), entity.getName(), entity.getEmail(), entity.getRole(), entity.getRegistrationDate()) : null;
    }

    public User convertDtoToEntity(UserRequestDto dto) {
        return dto != null ?
                new User(dto.getName(), dto.getEmail(), passwordEncoder.encode(dto.getPassword())) : null;
    }

    public User updateEntityWithDto(User entity, UserRequestDto dto) {
        if (dto.getName() != null && !dto.getName().isBlank()) {
            entity.setName(dto.getName());
        }
        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            entity.setEmail(dto.getEmail());
        }
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        return entity;
    }

    public User updateEntityWithAdminDto(User entity, UserRequestByAdminDto dto) {
        if (dto.getRole() != null && !dto.getRole().isBlank()) {
            entity.setRole(dto.getRole());
        }
        return updateEntityWithDto(entity, dto);
    }
}
