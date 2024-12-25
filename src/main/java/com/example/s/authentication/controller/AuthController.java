package com.example.s.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.s.authentication.dto.AuthRequest;
import com.example.s.authentication.dto.AuthResponse;
import com.example.s.authentication.dto.ChangePasswordRequest;
import com.example.s.authentication.service.AuthService;
import com.example.s.user.dto.UserResponse;
import com.example.s.utils.CustomizeResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {

        UserResponse response = authService.register(request);
        return CustomizeResponseEntity.buildResponse(HttpStatus.CREATED, "Success", response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        AuthResponse response = authService.login(request);
        return CustomizeResponseEntity.buildResponse(HttpStatus.OK, "Success", response);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
        UserResponse response = authService.changePassword(request);
        return CustomizeResponseEntity.buildResponse(HttpStatus.OK, "Success", response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> postMethodName(@RequestBody String refreshToken) {
        AuthResponse response = authService.refreshToken(refreshToken);
        return CustomizeResponseEntity.buildResponse(HttpStatus.OK, "Success", response);
    }
    
    
}
