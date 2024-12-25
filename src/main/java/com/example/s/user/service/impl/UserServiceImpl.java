package com.example.s.user.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.s.post.service.BlogService;
import com.example.s.user.dto.UserRequest;
import com.example.s.user.dto.UserResponse;
import com.example.s.user.model.entity.User;
import com.example.s.user.repository.UserRespository;
import com.example.s.user.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRespository userRespository;

    @Autowired
    private BlogService blogService;


    @Override
    public UserResponse createUser(UserRequest userRequest) {
        User user = User.builder()
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .contact(userRequest.getContact())
                .build();
        
        userRespository.save(user);
        return toUserResponse(user);
    }

    @Override
    public UserResponse deleteUser(UUID id) {
        
        if(userRespository.existsById(id)) {
            userRespository.deleteById(id);
        } else {
            throw new RuntimeException("User not found");
        }
        
        return toUserResponse(null);
    }

    @Override
    public UserResponse getUser(UUID id) {
        
        User user = userRespository.findById(id).orElse(null);

        return toUserResponse(user);
    }

    @Override
    public UserResponse updateUser(UUID id, UserRequest userRequest) {

        if (userRespository.existsById(id)) {
            User user = User.builder()
                    .id(id)
                    .username(userRequest.getUsername())
                    .email(userRequest.getEmail())
                    .contact(userRequest.getContact())
                    .build();

            userRespository.save(user);
            return toUserResponse(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }
    
    
    
    @Override
    public UserResponse getByUsername(String username) {
        User user = userRespository.findByUsername(username);
        return toUserResponse(user);
    }

    @Override
    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .contact(user.getContact())
                .blogs(user.getBlogs().stream().map(blogService::toBlogResponse).toList())
                .build();
    }
}
