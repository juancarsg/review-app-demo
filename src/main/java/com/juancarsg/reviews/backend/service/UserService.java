package com.juancarsg.reviews.backend.service;

import com.juancarsg.reviews.backend.dto.admin.UserRequestByAdminDto;
import com.juancarsg.reviews.backend.dto.user.UserRequestDto;
import com.juancarsg.reviews.backend.dto.user.UserResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    ResponseEntity<UserResponseDto> getUser(Long id);
    ResponseEntity<UserResponseDto> createUser(UserRequestDto creationDto);
    ResponseEntity<UserResponseDto> updateUser(Long id, UserRequestDto updateDto);
    ResponseEntity<UserResponseDto> updateUserByAdmin(Long id, UserRequestByAdminDto updateManagementDto);
    ResponseEntity<Void> deleteUser(Long id);
    UserDetails loadUserByUsername(String username);
}
