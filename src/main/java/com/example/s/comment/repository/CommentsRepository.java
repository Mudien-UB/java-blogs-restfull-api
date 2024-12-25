package com.example.s.comment.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import com.example.s.comment.model.entity.Comment;

@Repository
public interface CommentsRepository extends JpaRepositoryImplementation<Comment, UUID> {
    Page<Comment> findCommentsByBlogId(UUID blogId, Pageable pageable);
}
