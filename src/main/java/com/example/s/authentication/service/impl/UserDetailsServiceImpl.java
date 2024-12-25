package com.example.s.authentication.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.s.authentication.dto.UserDetailsImpl;
import com.example.s.user.model.entity.User;
import com.example.s.user.repository.UserRespository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRespository userRespository;

    private final String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        User user = null;
        if (!userId.matches(regex)) {
            user = userRespository.findById(UUID.fromString(userId)).orElse(null);
        } else {
            user = userRespository.findByEmail(userId);
        }

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return UserDetailsImpl.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }
    
}
