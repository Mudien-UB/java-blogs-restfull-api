package com.example.s.authentication.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChangePasswordRequest {
    private UUID id;
    private String oldPassword;
    private String newPassword;
}
