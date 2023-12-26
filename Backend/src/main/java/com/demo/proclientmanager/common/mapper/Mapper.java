package com.demo.proclientmanager.common.mapper;


import com.demo.proclientmanager.model.Customer;
import com.demo.proclientmanager.payload.dto.CustomerCreateDto;
import com.demo.proclientmanager.payload.dto.CustomerResponseDto;
;

public interface Mapper {

    CustomerResponseDto customerToCustomerResponceDto(Customer customer);
    Customer customerCreateDtoToCustomer(CustomerCreateDto customerCreateDto);

}