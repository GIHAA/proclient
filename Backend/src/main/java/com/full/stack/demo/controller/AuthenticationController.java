package com.full.stack.demo.controller;

import com.full.stack.demo.core.service.AuthenticationService;
import lombok.NonNull;
import org.springframework.web.bind.annotation.PostMapping;

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