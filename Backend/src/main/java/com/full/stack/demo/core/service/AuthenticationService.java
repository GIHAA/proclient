package com.full.stack.demo.core.service;

import com.full.stack.demo.core.payload.SigninRequest;
import com.full.stack.demo.core.payload.SignupRequest;
import com.full.stack.demo.core.payload.common.ResponseEntityDto;

public interface AuthenticationService {

    ResponseEntityDto signup(SignupRequest request);

    ResponseEntityDto signin(SigninRequest request);
}