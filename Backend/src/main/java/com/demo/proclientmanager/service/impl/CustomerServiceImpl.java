package com.demo.proclientmanager.service.impl;


import com.demo.proclientmanager.exception.ModuleException;
import com.demo.proclientmanager.model.Customer;
import com.demo.proclientmanager.payload.common.ErrorResponse;
import com.demo.proclientmanager.payload.common.ResponseEntityDto;
import com.demo.proclientmanager.payload.dto.CustomerCreateDto;
import com.demo.proclientmanager.payload.dto.CustomerEditDto;
import com.demo.proclientmanager.payload.dto.CustomerResponseDto;
import com.demo.proclientmanager.repository.CustomerDao;
import com.demo.proclientmanager.service.CustomerService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.demo.proclientmanager.common.ModuleConstants.AppErrorMessages.*;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    @NonNull
    private final CustomerDao customerDao;

    @NonNull
    private MessageSource messageSource;


    @Override
    public ResponseEntityDto createCustomer(CustomerCreateDto customerCreateDto) {

        Optional<Customer> checkCustomer = customerDao.findCustomerByEmail(customerCreateDto.getEmail());

        if(checkCustomer.isPresent()){
            throw new ModuleException(String.format(messageSource.getMessage(EMAIL_ALREADY_IN_USE , null, Locale.ENGLISH),
                    customerCreateDto.getEmail()));
        }
        if (    customerCreateDto.getFirstName() == null ||
                customerCreateDto.getLastName() == null ||
                customerCreateDto.getEmail() == null ||
                customerCreateDto.getPhoneNumber() == null ||
                customerCreateDto.getGender() == null ||
                customerCreateDto.getDob() == null) {
            throw new ModuleException(String.format(messageSource.getMessage(REQUEST_BODY_IS_MISSING_PAYLOAD , null, Locale.ENGLISH)));
        }


        Customer customer = customerCreateDtoToCustomer(customerCreateDto);
        String timestamp = String.valueOf(System.currentTimeMillis()).toString().substring(0,4);
        String uuid = UUID.randomUUID().toString().substring(0, 5);

        String id = String.format("%s-%s", timestamp, uuid);

        switch (customer.getGender()){
            case MALE:
                id = "M-"+id;
                break;
            case FEMALE:
                id = "F-"+id;
                break;
            case NOT_SPECIFIED:
                id = "N-"+id;
                break;
            case NON_BINARY:
                id = "NB-"+id;
                break;
            case PREFER_NOT_TO_SAY:
                id = "P-"+id;
                break;
            case OTHER:
                id = "O-"+id;
                break;
            default:
                System.out.println("Invalid gender");
        }

        customer.setId(id);

        Customer savedCustomer = customerDao.save(customer);

        return new ResponseEntityDto(false, savedCustomer);
    }

    @Override
    public ResponseEntityDto getCustomers(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Customer> customerPage = customerDao.findAll(pageRequest);

        return new ResponseEntityDto(false, customerPage);
    }

    @Override
    public ResponseEntityDto getOneCustomer(String id) {
        Optional<Customer> customer = customerDao.findById(id);

        if(customer.isEmpty()){
            throw new ModuleException(String.format(messageSource.getMessage(CUSTOMER_NOT_FOUND, null, Locale.ENGLISH),
                    id));
        }

        return new ResponseEntityDto(false, customer);
    }


    @Override
    public ResponseEntityDto editCustomer(CustomerEditDto customerEditDto) {

        Optional<Customer> existingCustomer = customerDao.findById(customerEditDto.getId());

        if(existingCustomer.isEmpty()){
            throw new ModuleException(String.format(messageSource.getMessage(CUSTOMER_NOT_FOUND, null, Locale.ENGLISH),
                    customerEditDto.getId()));
        }

        if (customerEditDto.getFirstName() != null) {
            existingCustomer.get().setFirstName(customerEditDto.getFirstName());
        }
        if (customerEditDto.getLastName() != null) {
            existingCustomer.get().setLastName(customerEditDto.getLastName());
        }
        if (customerEditDto.getEmail() != null) {
            existingCustomer.get().setEmail(customerEditDto.getEmail());
        }
        if (customerEditDto.getPhoneNumber() != null) {
            existingCustomer.get().setPhoneNumber(customerEditDto.getPhoneNumber());
        }
        if (customerEditDto.getGender() != null) {
            existingCustomer.get().setGender(customerEditDto.getGender());
        }
        if (customerEditDto.getDob() != null) {
            existingCustomer.get().setDob(customerEditDto.getDob());
        }

        Customer updatedCustomer = customerDao.save(existingCustomer.get());

        return new ResponseEntityDto(false, updatedCustomer);
    }

    @Override
    public ResponseEntityDto deleteCustomer(String id) {
        Customer customerToDelete = customerDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with id: " + id));

        customerDao.delete(customerToDelete);

        return new ResponseEntityDto(false, customerToDelete);
    }

    @Override
    public ResponseEntityDto searchCustomers(String searchField, String searchTerm, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Customer> searchResults = null;
        switch (searchField.toUpperCase()) {
            case "BOTH":
                searchResults = customerDao.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(searchTerm, searchTerm, searchTerm, pageRequest);
                break;
            case "NAME":
                searchResults = customerDao.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(searchTerm, searchTerm, pageRequest);
                break;
            case "EMAIL":
                searchResults = customerDao.findByEmailContainingIgnoreCase(searchTerm, pageRequest);
                break;
            default:
                searchResults = customerDao.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(searchTerm, searchTerm, searchTerm, pageRequest);
                break;
        }

        return new ResponseEntityDto(false, searchResults);
    }


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
