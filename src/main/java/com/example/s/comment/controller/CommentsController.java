package com.example.s.comment.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.s.comment.dto.CommentsRequest;
import com.example.s.comment.dto.CommentsResponse;
import com.example.s.comment.service.CommentsService;
import com.example.s.utils.CustomizeResponseEntity;

@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    @PostMapping("/add")
    public ResponseEntity<?> createComment(@RequestBody CommentsRequest request) {
        CommentsResponse response = commentsService.save(request);
        return CustomizeResponseEntity.buildResponse(HttpStatus.CREATED, "Success", response);
    }

    @GetMapping("/")
    public ResponseEntity<?> getById(@RequestParam UUID id) {
        CommentsResponse response = commentsService.get(id);
        return CustomizeResponseEntity.buildResponse(HttpStatus.OK, "Success", response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteComment(@RequestParam UUID id) {
        CommentsResponse response = commentsService.delete(id);
        return CustomizeResponseEntity.buildResponse(HttpStatus.OK, "Success", response);
    }

    @GetMapping("/comments")
    public ResponseEntity<?> getComments(
            @RequestParam UUID blogId,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return CustomizeResponseEntity.buildResponse(HttpStatus.OK, "Success", commentsService.getCommentsPage(blogId, pageRequest));
    }

    

}
