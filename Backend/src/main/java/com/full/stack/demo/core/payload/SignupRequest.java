package com.full.stack.demo.core.payload;

import com.full.stack.demo.core.type.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    protected LocalDate dob;
    protected Character gender;
    protected String location;
    private Role role;
}