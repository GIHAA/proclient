package com.full.stack.demo.core.payload.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserResponseDto {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private LocalDateTime dateOfBirth;
    private String userRole;
}
