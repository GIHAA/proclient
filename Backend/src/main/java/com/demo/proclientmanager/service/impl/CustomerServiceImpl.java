package com.demo.proclientmanager.service.impl;


import com.demo.proclientmanager.common.mapper.Mapper;
import com.demo.proclientmanager.model.Customer;
import com.demo.proclientmanager.payload.common.ResponseEntityDto;
import com.demo.proclientmanager.payload.dto.CustomerCreateDto;
import com.demo.proclientmanager.payload.dto.CustomerEditDto;
import com.demo.proclientmanager.payload.dto.CustomerResponseDto;
import com.demo.proclientmanager.repository.CustomerDao;
import com.demo.proclientmanager.service.CustomerService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    @NonNull
    private final CustomerDao customerDao;

    @NonNull
    private final Mapper mapper;

    @Override
    public ResponseEntityDto createCustomer(CustomerCreateDto customerCreateDto) {
        // Convert CustomerCreateDto to Customer entity
        Customer customer = mapper.customerCreateDtoToCustomer(customerCreateDto);
        System.out.println(customer);
        // Save the customer using the DAO
        Customer savedCustomer = customerDao.save(customer);

        CustomerResponseDto customerResponseDto  = mapper.customerToCustomerResponceDto(savedCustomer);
        // Convert the saved Customer entity to CustomerResponseDto and return
        return new ResponseEntityDto(false, customerResponseDto);
    }

    @Override
    public List<CustomerResponseDto> getCustomers() {
        // Retrieve all customers from the DAO
        List<Customer> customers = customerDao.findAll();

        // Convert the list of Customer entities to a list of CustomerResponseDto and return
        return customers.stream()
                .map(this::mapEntityToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerResponseDto editCustomer(CustomerEditDto customerEditDto) {
        // Retrieve the existing customer by ID
        Customer existingCustomer = customerDao.findById(customerEditDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with id: " + customerEditDto.getId()));

        // Update the existing customer with the new data
        updateCustomerFromEditDto(existingCustomer, customerEditDto);

        // Save the updated customer using the DAO
        Customer updatedCustomer = customerDao.save(existingCustomer);

        // Convert the updated Customer entity to CustomerResponseDto and return
        return mapEntityToResponseDto(updatedCustomer);
    }

    @Override
    public CustomerResponseDto deleteCustomer(String id) {
        // Retrieve the customer by ID
        Customer customerToDelete = customerDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with id: " + id));

        // Delete the customer using the DAO
        customerDao.delete(customerToDelete);

        // Convert the deleted Customer entity to CustomerResponseDto and return
        return mapEntityToResponseDto(customerToDelete);
    }

    // Helper method to map CustomerCreateDto to Customer entity
    private Customer mapCreateDtoToEntity(CustomerCreateDto createDto) {
        // Implement mapping logic here
        // Example: return new Customer(createDto.getName(), createDto.getEmail(), ...);
        return null;
    }

    // Helper method to map Customer entity to CustomerResponseDto
    private CustomerResponseDto mapEntityToResponseDto(Customer customer) {
        // Implement mapping logic here
        // Example: return new CustomerResponseDto(customer.getId(), customer.getName(), customer.getEmail(), ...);
        return null;
    }

    // Helper method to update Customer entity from CustomerEditDto
    private void updateCustomerFromEditDto(Customer customer, CustomerEditDto editDto) {
        // Implement update logic here
        // Example: customer.setName(editDto.getName()); customer.setEmail(editDto.getEmail()); ...
    }
}
