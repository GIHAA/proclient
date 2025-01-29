package com.demo.proclientmanager.model;

import com.demo.proclientmanager.common.types.Gender;
import com.demo.proclientmanager.model.audit.Auditable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document
@EqualsAndHashCode(callSuper = true)
public class Customer extends Auditable {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    @Indexed(unique = true)
    private String email;
    private String phoneNumber;
    private Gender gender;
    private Date dob;
}