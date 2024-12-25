package com.example.s.authentication.service.impl;

import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.s.authentication.dto.AuthRequest;
import com.example.s.authentication.dto.AuthResponse;
import com.example.s.authentication.dto.ChangePasswordRequest;
import com.example.s.authentication.jwt.JwtProvider;
import com.example.s.authentication.service.AuthService;
import com.example.s.user.dto.UserResponse;
import com.example.s.user.model.entity.User;
import com.example.s.user.repository.UserRespository;

@Service
public class AuthServiceImpl implements AuthService {
    
    @Autowired
    private UserRespository userRespository;

    @Autowired
    private JwtProvider jwtProvider;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public AuthResponse login(AuthRequest authRequest) {

        try{

            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(auth);

            User user = userRespository.findByEmail(authRequest.getEmail());

            if (user == null) {
                throw new RuntimeException("User not found");
            }

            String accessToken = jwtProvider.generateAccessToken( user.getId().toString());
            String refreshToken = jwtProvider.generateRefreshToken(user.getId().toString());

            return AuthResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        
        } catch (Exception e) {
            throw new RuntimeException("Login failed : " + e.getMessage());
        }
        
    }

    @Override
    public UserResponse register(AuthRequest authRequest) {
        User newUser = User.builder()
                .username("user"+ String.valueOf(new Random().nextInt(1000)))
                .email(authRequest.getEmail())
                .password(hashPassword(authRequest.getPassword() ))
                .build();

        userRespository.save(newUser);
        return UserResponse.builder()
                .id(newUser.getId())
                .username(newUser.getUsername())
                .email(newUser.getEmail())
                .contact(newUser.getContact())
                .build();
    }

    
    @Override
    public UserResponse changePassword(ChangePasswordRequest request) {
        User user = userRespository.findById(request.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            user.setPassword(hashPassword(request.getNewPassword()));
            userRespository.save(user);
            return UserResponse.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .contact(user.getContact())
                    .build();
        } else {
            throw new RuntimeException("Old password is incorrect");
        }

    }
    
    

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        
        String userId = jwtProvider.extractUserId(refreshToken);
        
        if (userRespository.existsById(UUID.fromString(userId))) {
            String accessToken = jwtProvider.generateAccessToken(userId);
            String newRefreshToken = jwtProvider.generateRefreshToken(userId);
            return AuthResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(newRefreshToken)
                    .build();
        } else {
            throw new RuntimeException("User not found");
        }
    }

    private String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    
}
