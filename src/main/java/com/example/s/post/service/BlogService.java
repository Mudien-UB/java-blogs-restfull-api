package com.example.s.post.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;

import com.example.s.post.dto.BlogRequest;
import com.example.s.post.dto.BlogResponse;
import com.example.s.post.model.entity.Blog;

public interface BlogService{
    
    BlogResponse createBlog(BlogRequest blogRequest);

    BlogResponse getBlog(UUID id);
    
    BlogResponse updateBlog(UUID id, BlogRequest blogRequest);

    BlogResponse deleteBlog(UUID id);

    BlogResponse getBlogBySlug(String title);

    BlogResponse toBlogResponse(Blog blog);

    List<BlogResponse> getBlogsPage(PageRequest pageRequest);

}
