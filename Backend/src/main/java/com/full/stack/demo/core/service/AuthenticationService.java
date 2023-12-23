package com.full.stack.demo.core.service;

public interface AuthenticationService {

    ResponseEntityDto signup(SignupRequest request);

    ResponseEntityDto signin(SigninRequest request);
}