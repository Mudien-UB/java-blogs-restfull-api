package com.example.s.post.dto;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.example.s.comment.dto.CommentsResponse;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BlogResponse {

    private UUID id;
    private String title;
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    private UUID userId;
    private List<CommentsResponse> comments;
}
