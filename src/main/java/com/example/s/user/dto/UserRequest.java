package com.example.s.user.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserRequest {
    private String username;
    private String email;
    private String contact;
}
