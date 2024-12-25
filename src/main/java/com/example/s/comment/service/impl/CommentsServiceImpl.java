package com.example.s.comment.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.s.comment.dto.CommentsRequest;
import com.example.s.comment.dto.CommentsResponse;
import com.example.s.comment.model.entity.Comment;
import com.example.s.comment.repository.CommentsRepository;
import com.example.s.comment.service.CommentsService;
import com.example.s.post.model.entity.Blog;
import com.example.s.post.repository.BlogRepository;
import com.example.s.user.model.entity.User;
import com.example.s.user.repository.UserRespository;

@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private UserRespository userRespository;

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public CommentsResponse delete(UUID id) {

        if(commentsRepository.existsById(id)) {
            commentsRepository.deleteById(id);
            return toCommentsResponse(null);
        } else {
            throw new IllegalArgumentException("Comment with id " + id + " not found");
        }
    }

    @Override
    public CommentsResponse get(UUID id) {
        Comment comment = commentsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comment with id " + id + " not found"));
        return toCommentsResponse(comment);
    }

    @Override
    public CommentsResponse save(CommentsRequest commentsRequest) {
        User user = userRespository.findById(commentsRequest.getUserId()).get();
        Blog blog = blogRepository.findById(commentsRequest.getBlogId()).get();

        if (user == null || blog == null) {
            throw new IllegalArgumentException("Somethings wrong");
        }

        Comment newComment = Comment.builder()
                .content(commentsRequest.getContent())
                .createdAt(LocalDateTime.now())
                .blog(blog)
                .user(user)
                .build();

        commentsRepository.save(newComment);
        return toCommentsResponse(newComment);
    }

    @Override
    public CommentsResponse toCommentsResponse(Comment comment) {
        return CommentsResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .userId(comment.getUser().getId())
                .blogId(comment.getBlog().getId())
                .createdAt(comment.getCreatedAt())
                .build();
    }

    @Override
    public List<CommentsResponse> getCommentsPage(UUID blogId, PageRequest pageRequest) {
        Sort sort = Sort.by("blogId").and(Sort.by("created_at").descending());
        Pageable pageable = PageRequest.of(pageRequest.getPageNumber(), pageRequest.getPageSize(), sort);
        List<Comment> comments = commentsRepository.findCommentsByBlogId(blogId, pageable).getContent();
        if (comments.isEmpty())
            throw new IllegalArgumentException("Comments not found");
        return comments.stream().map(this::toCommentsResponse).toList();
    }

    
    
}
