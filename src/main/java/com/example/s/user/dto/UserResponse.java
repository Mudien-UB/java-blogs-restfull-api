package com.example.s.user.dto;

import java.util.List;
import java.util.UUID;

import com.example.s.post.dto.BlogResponse;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserResponse {
    private UUID id;
    private String username;
    private String email;
    private String contact;
    private List<BlogResponse> blogs;
}
