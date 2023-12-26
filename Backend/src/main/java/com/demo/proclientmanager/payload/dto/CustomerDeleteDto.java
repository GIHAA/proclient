package com.demo.proclientmanager.payload.dto;

import com.demo.proclientmanager.common.types.Gender;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CustomerDeleteDto {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Gender gender;
    private Date dob;
}
