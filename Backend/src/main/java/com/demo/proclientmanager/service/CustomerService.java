package com.demo.proclientmanager.service;

import com.demo.proclientmanager.payload.dto.CustomerCreateDto;
import com.demo.proclientmanager.payload.dto.CustomerEditDto;
import com.demo.proclientmanager.payload.dto.CustomerResponseDto;

import java.util.List;

public interface CustomerService {
    CustomerResponseDto creatCustomer(CustomerCreateDto customer);
    List<CustomerCreateDto> getCustomers();
    CustomerResponseDto editCustomer(CustomerEditDto customer);


}
