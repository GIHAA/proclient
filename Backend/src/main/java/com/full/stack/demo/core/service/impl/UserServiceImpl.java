package com.full.stack.demo.core.service.impl;

import com.full.stack.demo.core.model.User;
import com.full.stack.demo.core.payload.SignupRequest;
import com.full.stack.demo.core.payload.dto.UserResponseDto;
import com.full.stack.demo.core.repository.UserDao;
import com.full.stack.demo.core.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Locale;

import static com.full.stack.demo.core.common.ModuleConstants.ServerErrorMessages.USER_NOT_FOUND;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    @NonNull
    private final UserDao userDao;

    @NonNull
    private final MessageSource messageSource;

    @Override
    public UserDetailsService userDetailsService() {
        return username -> userDao.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(messageSource.getMessage(USER_NOT_FOUND, null, Locale.ENGLISH)));
    }

    @Override
    public User getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (User) userDetails;
    }
    @Override
    public UserResponseDto CreateUser(SignupRequest signupRequest) {
        return null;
    }
}
