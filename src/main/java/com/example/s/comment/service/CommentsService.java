package com.example.s.comment.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;

import com.example.s.comment.dto.CommentsRequest;
import com.example.s.comment.dto.CommentsResponse;
import com.example.s.comment.model.entity.Comment;

public interface CommentsService {
    CommentsResponse save(CommentsRequest commentsRequest);
    
    CommentsResponse delete(UUID id);

    CommentsResponse get(UUID id);

    CommentsResponse toCommentsResponse(Comment comment);

    List<CommentsResponse> getCommentsPage(UUID blogId, PageRequest pageRequest);
}
