package com.full.stack.demo.core.service;

import com.full.stack.demo.core.model.User;
import com.full.stack.demo.core.payload.SignupRequest;
import com.full.stack.demo.core.payload.dto.UserResponseDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    UserDetailsService userDetailsService();
    User getCurrentUser();
    UserResponseDto CreateUser(SignupRequest signupRequest);
}
