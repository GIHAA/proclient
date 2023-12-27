package com.demo.proclientmanager.service;

import com.demo.proclientmanager.payload.common.ResponseEntityDto;
import com.demo.proclientmanager.payload.dto.CustomerCreateDto;
import com.demo.proclientmanager.payload.dto.CustomerEditDto;


import java.util.List;

public interface CustomerService {
    ResponseEntityDto createCustomer(CustomerCreateDto customer);
    ResponseEntityDto getCustomers();
    ResponseEntityDto getOneCustomer(String id);

    ResponseEntityDto deleteCustomer(String id);
    ResponseEntityDto editCustomer(CustomerEditDto customer);

}
