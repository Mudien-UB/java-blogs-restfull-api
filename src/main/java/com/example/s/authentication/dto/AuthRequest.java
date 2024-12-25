package com.example.s.authentication.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthRequest {
    private String email;
    private String password;
}
