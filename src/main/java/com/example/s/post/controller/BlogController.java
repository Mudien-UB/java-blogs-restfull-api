package com.example.s.post.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.s.post.dto.BlogRequest;
import com.example.s.post.dto.BlogResponse;
import com.example.s.post.service.BlogService;
import com.example.s.utils.CustomizeResponseEntity;

@RestController
@RequestMapping("/blog")
public class BlogController {
    

    @Autowired
    private BlogService blogService;

    @GetMapping("/{slug}")
    public ResponseEntity<?> getBlog(@PathVariable String slug) {
        BlogResponse blogResponse = blogService.getBlogBySlug(slug);
        return CustomizeResponseEntity.buildResponse(HttpStatus.OK, "Success", blogResponse);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createBlog(@RequestBody BlogRequest request) {
        BlogResponse response = blogService.createBlog(request);
        return CustomizeResponseEntity.buildResponse(HttpStatus.CREATED, "Success", response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBlog(@PathVariable UUID id, @RequestBody BlogRequest request) {
        BlogResponse response = blogService.updateBlog(id, request);
        return CustomizeResponseEntity.buildResponse(HttpStatus.OK, "Success", response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBlog(@PathVariable UUID id) {
        BlogResponse response = blogService.deleteBlog(id);
        return CustomizeResponseEntity.buildResponse(HttpStatus.OK, "Success", response);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getBlog(@PathVariable UUID id) {
        BlogResponse blogResponse = blogService.getBlog(id);
        return CustomizeResponseEntity.buildResponse(HttpStatus.OK, "Success", blogResponse);
    }

    @GetMapping("/blogs")
    public ResponseEntity<?> getBlogs(
        @RequestParam(name = "page", required = false, defaultValue = "1") int page,
        @RequestParam(name = "size", required = false, defaultValue = "10") int size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return CustomizeResponseEntity.buildResponse(HttpStatus.OK, "Success", blogService.getBlogsPage(pageRequest));
    }

}
