package com.example.s.user.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import com.example.s.user.model.entity.User;

@Repository
public interface UserRespository extends JpaRepositoryImplementation<User, UUID>{
    User findByUsername(String username);
    User findByEmail(String email);
}
