package com.andrew.user_service.controller;


import com.andrew.user_service.dto.ApiResponse;
import com.andrew.user_service.dto.UserDetailsRequest;
import com.andrew.user_service.dto.UserRequest;
import com.andrew.user_service.dto.UserResponse;
import com.andrew.user_service.entity.User;
import com.andrew.user_service.entity.UserDetails;
import com.andrew.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService service;

    @GetMapping()
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers(){
        List<UserResponse> users = service.getAllUsers();
        return ResponseEntity.ok(new ApiResponse<>("All users",
                HttpStatus.OK.value(),
                LocalDateTime.now(),
                users));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long userId){
        UserResponse user = service.getUserById(userId);
        return ResponseEntity.ok(new ApiResponse<>("User found with id: " + userId,
                HttpStatus.OK.value(),
                LocalDateTime.now(),
                user));
    }

    @GetMapping("/{userId}/details")
    public ResponseEntity<ApiResponse<UserDetails>> getUserDetailsById(@PathVariable Long userId){
        UserDetails userDetails = service.getUserDetailsById(userId);
        return ResponseEntity.ok(new ApiResponse<>("User found with id: " + userId,
                HttpStatus.OK.value(),
                LocalDateTime.now(),
                userDetails));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@RequestBody @Valid UserRequest request){
        UserResponse user = service.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("User created successfully!",
                        HttpStatus.CREATED.value(),
                        LocalDateTime.now(),
                        user));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable Long userId, @RequestBody @Valid UserDetailsRequest request){
        UserResponse user = service.updateUser(userId, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("User updated successfully!",
                        HttpStatus.OK.value(),
                        LocalDateTime.now(),
                        user));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> deleteUserById(@PathVariable Long userId){
        service.deleteUserById(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("User Deleted successfully!",
                        HttpStatus.OK.value(),
                        LocalDateTime.now(),
                        null));
    }


}
