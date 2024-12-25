package com.example.s.post.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.s.comment.service.CommentsService;
import com.example.s.post.dto.BlogRequest;
import com.example.s.post.dto.BlogResponse;
import com.example.s.post.model.entity.Blog;
import com.example.s.post.repository.BlogRepository;
import com.example.s.post.service.BlogService;
import com.example.s.user.model.entity.User;
import com.example.s.user.repository.UserRespository;

import jakarta.transaction.Transactional;



@Service
@Transactional
public class BlogServiceImpl implements BlogService {


    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserRespository userRespository;

    @Autowired
    private CommentsService commentsService;

    @Override
    public BlogResponse createBlog(BlogRequest blogRequest) {
        Optional<User> userOptional = userRespository.findById(blogRequest.getUserId());
        if (userOptional.isEmpty())
            throw new IllegalArgumentException("User with id " + blogRequest.getUserId() + " not found");

        User user = userOptional.get();

        Blog blog = Blog.builder()
                .title(blogRequest.getTitle())
                .slug(toSlug(blogRequest.getTitle().toLowerCase()))
                .content(blogRequest.getContent())
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        blogRepository.save(blog);
        return BlogResponse.builder()
                .id(blog.getId())
                .title(blog.getTitle())
                .content(blog.getContent())
                .createdAt(blog.getCreatedAt())
                .userId(blog.getUser().getId())
                .build();
    }

    @Override
    public BlogResponse deleteBlog(UUID id) {
        Blog blog = blogRepository.findById(id).orElse(null);
        if (blog != null) {
            blogRepository.delete(blog);
            return toBlogResponse(null);
        } else {
            throw new IllegalArgumentException("Blog with id " + id + " not found");
        }
    }

    @Override
    public BlogResponse getBlog(UUID id) {

        Blog blog = blogRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Blog with id " + id + " not found"));

        return toBlogResponse(blog);
    }

    @Override
    public BlogResponse getBlogBySlug(String slug) {
        Blog blog = blogRepository.findBySlug(slug);
        return toBlogResponse(blog);
    }

    @Override
    public BlogResponse updateBlog(UUID id, BlogRequest blogRequest) {

        Optional<Blog> blogOptional = blogRepository.findById(id);
        if (blogOptional.isEmpty())
            throw new IllegalArgumentException("Blog with id " + id + " not found");

        Blog blog = blogOptional.get();

        blog.setTitle(blogRequest.getTitle());
        blog.setSlug(toSlug(blogRequest.getTitle().toLowerCase()));
        blog.setContent(blogRequest.getContent());
        blogRepository.save(blog);
        return toBlogResponse(blog);
    }
    

    @Override
    public BlogResponse toBlogResponse(Blog blog) {

        return BlogResponse.builder()
                .id(blog.getId())
                .title(blog.getTitle())
                .content(blog.getContent())
                .createdAt(blog.getCreatedAt())
                .userId(blog.getUser().getId())
                .comments(blog.getComments().stream().map(commentsService::toCommentsResponse).toList())
                .build();
    }

    

    @Override
    public List<BlogResponse> getBlogsPage(PageRequest pageRequest) {
        Pageable pageable = PageRequest.of(pageRequest.getPageNumber(), pageRequest.getPageSize());
        List<Blog> blogs = blogRepository.findAll(pageable).getContent();
        if (blogs.isEmpty())
            throw new IllegalArgumentException("Blogs not found");

        return blogs.stream().map(this::toBlogResponse).toList();
    }

    private String toSlug(String title) {
        return title.replace(" ", "-");
    }

    
    
}
