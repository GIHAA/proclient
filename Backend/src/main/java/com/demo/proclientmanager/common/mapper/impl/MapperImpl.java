package com.demo.proclientmanager.common.mapper.impl;

import com.demo.proclientmanager.common.mapper.Mapper;
import com.demo.proclientmanager.model.Customer;
import com.demo.proclientmanager.payload.dto.CustomerCreateDto;
import com.demo.proclientmanager.payload.dto.CustomerResponseDto;

public class MapperImpl implements Mapper {
    @Override
    public CustomerResponseDto customerToCustomerResponceDto(Customer customer) {
        CustomerResponseDto customerResponseDto = new CustomerResponseDto();
        customerResponseDto.setId(customer.getId());
        customerResponseDto.setGender(customer.getGender());
        customerResponseDto.setEmail(customer.getEmail());
        customerResponseDto.setFirstName(customer.getFirstName());
        customerResponseDto.setLastName(customer.getLastName());
        customerResponseDto.setDob(customer.getDob());
        customerResponseDto.setPhoneNumber(customer.getPhoneNumber());

        return  customerResponseDto;
    }

    @Override
    public Customer customerCreateDtoToCustomer(CustomerCreateDto customerCreateDto) {
        Customer customer = new Customer();
        customer.setId(customerCreateDto.getId());
        customer.setGender(customerCreateDto.getGender());
        customer.setEmail(customerCreateDto.getEmail());
        customer.setFirstName(customerCreateDto.getFirstName());
        customer.setLastName(customerCreateDto.getLastName());
        customer.setDob(customerCreateDto.getDob());
        customer.setPhoneNumber(customerCreateDto.getPhoneNumber());

        return  customer;
    }
}
