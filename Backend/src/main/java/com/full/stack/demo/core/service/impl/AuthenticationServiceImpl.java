package com.full.stack.demo.core.service.impl;

import com.full.stack.demo.core.model.User;
import com.full.stack.demo.core.repository.UserDao;
import com.full.stack.demo.core.service.AuthenticationService;
import com.full.stack.demo.core.service.JwtService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    @NonNull
    private final UserDao userDao;

    @NonNull
    private final PasswordEncoder passwordEncoder;

    @NonNull
    private final JwtService jwtService;

    @NonNull
    private final AuthenticationManager authenticationManager;

    @NonNull
    private final MessageSource messageSource;

//    @NonNull
//    private final EmployeeService employeeService;
//
//    @NonNull
//    private final SupplierService supplierService;

    @NonNull
    private final MapStructMapper mapper;

    @Override
    public ResponseEntityDto signup(SignupRequest request) {
        var user = User
                .builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole()).build();
        userDao.save(user);
        var jwt = jwtService.generateToken(user);

        JwtAuthenticationResponse response = JwtAuthenticationResponse.builder().token(jwt).build();
        if(request.getRole().equals(Role.SUPPLIER)){
            SupplierCreateDto supplierCreateDto = new SupplierCreateDto(request.getFirstName(), request.getLocation(), request.getEmail());
            SupplierResponseDto supplierResponseDto = supplierService.createSupplier(supplierCreateDto, user);
            response.setSupplier(supplierResponseDto);
        }
        else {
            EmployeeResponseDto employeeResponseDto = employeeService.createEmployee(mapper.employeeToEmployeeDto(request), user);
            response.setEmployee(employeeResponseDto);
        }

        return new ResponseEntityDto(false, response);
    }

    @Override
    public ResponseEntityDto signin(SigninRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userDao
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException(messageSource.getMessage(USER_LOGIN_FAILED, null, Locale.ENGLISH)));
        var jwt = jwtService.generateToken(user);

        return new ResponseEntityDto(false, JwtAuthenticationResponse.builder().token(jwt).build());
    }
}