package com.example.s.post.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import com.example.s.post.model.entity.Blog;

@Repository
public interface BlogRepository extends JpaRepositoryImplementation<Blog, UUID> {
    Blog findByTitle(String title);

    List<Blog> findByUserId(UUID userId);
    Blog findBySlug(String slug);
}
