package com.andrew.user_service.service;

import com.andrew.user_service.dto.UserDetailsRequest;
import com.andrew.user_service.dto.UserRequest;
import com.andrew.user_service.dto.UserResponse;
import com.andrew.user_service.entity.User;
import com.andrew.user_service.entity.UserDetails;
import com.andrew.user_service.exception.UserNotFoundException;
import com.andrew.user_service.repository.UserDetailsRepository;
import com.andrew.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserDetailsRepository userDetailsRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public List<UserResponse> getAllUsers() {
        return repository.findAll().stream()
                .map(user -> new UserResponse(user.getUserId(), user.getEmail(), user.getCreatedAt()))
                .toList();
    }

    public UserResponse getUserById(Long userId) {
        return repository.findById(userId)
                .map(user -> new UserResponse(user.getUserId(), user.getEmail(), user.getCreatedAt()))
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
    }

    public UserDetails getUserDetailsById(Long userId){
        return userDetailsRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
    }

    @Transactional
    public UserResponse createUser(UserRequest request) {
        if(repository.existsByEmail(request.email())){
            throw new IllegalArgumentException("Already have an account with this email!");
        }
        User user = new User();
        user.setEmail(request.email());
        user.setPassword(encoder.encode(request.password()));
        user.setCreatedAt(LocalDateTime.now());

        user = repository.save(user);

        UserDetails userDetails = new UserDetails();
        userDetails.setFirstName(request.firstName());
        userDetails.setLastName(request.lastName());
        userDetails.setAddress(request.address());
        userDetails.setPhoneNumber(request.phoneNumber());
        userDetails.setUser(user);

        userDetailsRepository.save(userDetails);

        user.setUserDetails(userDetails);
        repository.save(user);

        return new UserResponse(user.getUserId(), user.getEmail(), user.getCreatedAt());
    }

    @Transactional
    public UserResponse updateUser(Long userId, UserDetailsRequest request) {

        User user = repository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        UserDetails userDetails = userDetailsRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        userDetails.setFirstName(request.firstName());
        userDetails.setLastName(request.lastName());
        userDetails.setAddress(request.address());
        userDetails.setPhoneNumber(request.phoneNumber());
        userDetails.setUser(user);

        userDetailsRepository.save(userDetails);

        user.setUserDetails(userDetails);
        repository.save(user);

        return new UserResponse(user.getUserId(), user.getEmail(), user.getCreatedAt());    }

    @Transactional
    public void deleteUserById(Long userId) {
        if(!repository.existsById(userId)){
            throw new UserNotFoundException("User not found with id: " + userId);
        }
        repository.deleteById(userId);
    }
}
