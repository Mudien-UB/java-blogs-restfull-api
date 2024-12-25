package com.example.s.user.service;

import java.util.UUID;

import com.example.s.user.dto.UserRequest;
import com.example.s.user.dto.UserResponse;
import com.example.s.user.model.entity.User;

public interface UserService {

    UserResponse createUser(UserRequest userRequest);

    UserResponse getUser(UUID id);

    UserResponse updateUser(UUID id, UserRequest userRequest);

    UserResponse deleteUser(UUID id);

    UserResponse getByUsername(String username);

    UserResponse toUserResponse(User user);
    
}
