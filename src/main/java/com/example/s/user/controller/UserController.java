package com.example.s.user.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.s.utils.CustomizeResponseEntity;
import com.example.s.user.dto.UserRequest;
import com.example.s.user.dto.UserResponse;
import com.example.s.user.service.UserService;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;

    @PutMapping("/")
    public ResponseEntity<?> updateUser(@RequestParam UUID id, @RequestBody UserRequest userRequest) {
        UserResponse response = userService.updateUser(id, userRequest);
        return CustomizeResponseEntity.buildResponse(HttpStatus.OK, "Success", response);
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<?> getUser(@RequestParam UUID id) {
        UserResponse response = userService.getUser(id);
        return CustomizeResponseEntity.buildResponse(HttpStatus.OK, "Success", response);
    }

    @GetMapping("/get-by-name")
    public ResponseEntity<?> getByname(@RequestParam String username) {
        UserResponse response = userService.getByUsername(username);
        return CustomizeResponseEntity.buildResponse(HttpStatus.OK, "Success", response);
    }

    

    

}
