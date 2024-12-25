package com.example.s.post.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BlogRequest {
    private UUID userId;
    private String title;
    private String content;
}
