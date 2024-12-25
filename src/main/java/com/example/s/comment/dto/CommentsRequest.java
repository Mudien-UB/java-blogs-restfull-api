package com.example.s.comment.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentsRequest {
    private String content;
    private UUID blogId;
    private UUID userId;
}
