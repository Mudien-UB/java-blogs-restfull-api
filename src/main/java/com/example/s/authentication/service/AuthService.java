package com.example.s.authentication.service;

import com.example.s.authentication.dto.AuthRequest;
import com.example.s.authentication.dto.AuthResponse;
import com.example.s.authentication.dto.ChangePasswordRequest;
import com.example.s.user.dto.UserResponse;

public interface AuthService {
    
    AuthResponse login(AuthRequest authRequest);

    UserResponse register(AuthRequest authRequest);
    
    UserResponse changePassword(ChangePasswordRequest request);

    AuthResponse refreshToken(String refreshToken);
}
