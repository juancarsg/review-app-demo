package com.juancarsg.reviews.backend.controller;

import com.juancarsg.reviews.backend.dto.admin.UserRequestByAdminDto;
import com.juancarsg.reviews.backend.dto.user.UserRequestDto;
import com.juancarsg.reviews.backend.dto.user.UserResponseDto;
import com.juancarsg.reviews.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.juancarsg.reviews.backend.controller.UserController.API_PATH_USERS;

@RestController
@RequestMapping(API_PATH_USERS)
public class UserController {

    public static final String API_PATH_USERS = "/api/v1/users";

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserResponseDto> getUser(@RequestParam Long id) {
        return userService.getUser(id);
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Validated(UserRequestDto.ValidationGroups.Create.class) @RequestBody UserRequestDto requestDto) {
        return userService.createUser(requestDto);
    }

    @PatchMapping
    public ResponseEntity<UserResponseDto> updateUser(@RequestParam Long id,
                                                      @Validated(UserRequestDto.ValidationGroups.Update.class) @RequestBody UserRequestDto requestDto) {
        return userService.updateUser(id, requestDto);
    }

    @PutMapping
    public ResponseEntity<UserResponseDto> updateUserByAdmin(@RequestParam Long id,
                                                             @RequestBody UserRequestByAdminDto requestDto) {
        return userService.updateUserByAdmin(id, requestDto);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestParam Long id) {
        return userService.deleteUser(id);
    }

}
