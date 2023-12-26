package com.demo.proclientmanager.service;

import com.demo.proclientmanager.payload.common.ResponseEntityDto;
import com.demo.proclientmanager.payload.dto.CustomerCreateDto;
import com.demo.proclientmanager.payload.dto.CustomerEditDto;
import com.demo.proclientmanager.payload.dto.CustomerResponseDto;

import java.util.List;

public interface CustomerService {
    ResponseEntityDto createCustomer(CustomerCreateDto customer);
    List<CustomerResponseDto> getCustomers();
    CustomerResponseDto editCustomer(CustomerEditDto customer);

    CustomerResponseDto deleteCustomer(String id);

}
