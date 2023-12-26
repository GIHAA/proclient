package com.demo.proclientmanager.controller;


import com.demo.proclientmanager.payload.common.ResponseEntityDto;
import com.demo.proclientmanager.payload.dto.CustomerCreateDto;
import com.demo.proclientmanager.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customer")
@AllArgsConstructor
public class CustomerController {

    @NonNull
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<ResponseEntityDto> signup(@RequestBody CustomerCreateDto customerCreateDto) {
        ResponseEntityDto response = customerService.createCustomer(customerCreateDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }



}
