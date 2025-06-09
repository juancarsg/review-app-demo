package com.juancarsg.reviews.backend.service.impl;

import com.juancarsg.reviews.backend.dto.admin.UserRequestByAdminDto;
import com.juancarsg.reviews.backend.dto.user.UserRequestDto;
import com.juancarsg.reviews.backend.dto.user.UserResponseDto;
import com.juancarsg.reviews.backend.entity.User;
import com.juancarsg.reviews.backend.exception.CustomException;
import com.juancarsg.reviews.backend.repository.UserRepository;
import com.juancarsg.reviews.backend.service.UserService;
import com.juancarsg.reviews.backend.util.UserUtils;
import com.juancarsg.reviews.backend.util.mapper.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public ResponseEntity<UserResponseDto> getUser(Long id) {
        User user = findByIdOrThrow(id);
        return ResponseEntity.ok(userMapper.convertEntityToDto(user));
    }

    public ResponseEntity<UserResponseDto> createUser(UserRequestDto creationDto) {
        if (userRepository.existsByName(creationDto.getName()) || userRepository.existsByEmail(creationDto.getEmail())) {
            throw new CustomException("A user already exists with this name or email.", HttpStatus.BAD_REQUEST.value());
        }
        User user = userRepository.save(userMapper.convertDtoToEntity(creationDto));
        return ResponseEntity.ok(userMapper.convertEntityToDto(user));
    }

    public ResponseEntity<UserResponseDto> updateUser(Long id, UserRequestDto updateDto) {
        User user = findByIdOrThrow(id);
        if (!user.equals(UserUtils.getAuthenticatedUser())) {
            throw new AuthorizationDeniedException("You don't have permission to perform this action.");
        }
        user = userMapper.updateEntityWithDto(user, updateDto);
        user = userRepository.save(user);
        return ResponseEntity.ok(userMapper.convertEntityToDto(user));
    }

    public ResponseEntity<UserResponseDto> updateUserByAdmin(Long id, UserRequestByAdminDto updateManagementDto) {
        User user = findByIdOrThrow(id);
        user = userMapper.updateEntityWithAdminDto(user, updateManagementDto);
        user = userRepository.save(user);
        return ResponseEntity.ok(userMapper.convertEntityToDto(user));
    }

    public ResponseEntity<Void> deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.getUserByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with name " + username + " not found."));
    }

    private User findByIdOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found."));
    }

}
