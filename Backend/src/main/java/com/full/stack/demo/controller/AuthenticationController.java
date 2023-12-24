package com.full.stack.demo.controller;

import com.full.stack.demo.core.payload.SigninRequest;
import com.full.stack.demo.core.payload.SignupRequest;
import com.full.stack.demo.core.payload.common.ResponseEntityDto;
import com.full.stack.demo.core.service.AuthenticationService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @NonNull
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseEntityDto> signup(@RequestBody SignupRequest request) {
        ResponseEntityDto response = authenticationService.signup(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<ResponseEntityDto> signin(@RequestBody SigninRequest request) {
        ResponseEntityDto response = authenticationService.signin(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}