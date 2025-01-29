package com.demo.proclientmanager.service.impl;

import com.demo.proclientmanager.exception.ModuleException;
import com.demo.proclientmanager.model.Customer;
import com.demo.proclientmanager.payload.common.ResponseEntityDto;
import com.demo.proclientmanager.payload.dto.CustomerCreateDto;
import com.demo.proclientmanager.payload.dto.CustomerEditDto;
import com.demo.proclientmanager.repository.CustomerDao;
import com.demo.proclientmanager.service.CustomerService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.demo.proclientmanager.common.types.Gender;

import java.util.*;

import static com.demo.proclientmanager.common.ModuleConstants.AppErrorMessages.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    @NonNull
    private final CustomerDao customerDao;

    @NonNull
    private final MessageSource messageSource;

    @Override
    @Transactional
    public ResponseEntityDto createCustomer(CustomerCreateDto customerCreateDto) {
        try {
            log.info("Starting customer creation process for email: {}", customerCreateDto.getEmail());
            MDC.put("customerEmail", customerCreateDto.getEmail());

            validateNewCustomer(customerCreateDto);
            Customer customer = customerCreateDtoToCustomer(customerCreateDto);
            String id = generateCustomerId(customer.getGender());
            customer.setId(id);
            
            MDC.put("customerId", id);
            log.debug("Generated customer ID: {}", id);

            Customer savedCustomer = customerDao.save(customer);
            log.info("Successfully created customer with ID: {}", id);
            
            return new ResponseEntityDto(false, savedCustomer);
        } catch (ModuleException e) {
            log.error("Failed to create customer: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error during customer creation", e);
            throw new ModuleException("Unexpected error during customer creation");
        } finally {
            MDC.clear();
        }
    }

    @Override
    public ResponseEntityDto getCustomers(int page, int size) {
        try {
            log.info("Fetching customers page: {}, size: {}", page, size);
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<Customer> customerPage = customerDao.findAll(pageRequest);
            log.debug("Found {} customers", customerPage.getTotalElements());
            
            return new ResponseEntityDto(false, customerPage);
        } catch (Exception e) {
            log.error("Error fetching customers", e);
            throw new ModuleException("Error fetching customers");
        }
    }

    @Override
    public ResponseEntityDto getOneCustomer(String id) {
        try {
            MDC.put("customerId", id);
            log.info("Fetching customer with ID: {}", id);
            
            Optional<Customer> customer = customerDao.findById(id);
            if (customer.isEmpty()) {
                log.warn("Customer not found with ID: {}", id);
                throw new ModuleException(String.format(
                    messageSource.getMessage(CUSTOMER_NOT_FOUND, null, Locale.ENGLISH), id));
            }
            
            log.debug("Successfully retrieved customer: {}", id);
            return new ResponseEntityDto(false, customer);
        } finally {
            MDC.clear();
        }
    }

    @Override
    @Transactional
    public ResponseEntityDto editCustomer(CustomerEditDto customerEditDto) {
        try {
            String customerId = customerEditDto.getId();
            MDC.put("customerId", customerId);
            log.info("Starting customer update process for ID: {}", customerId);

            Optional<Customer> existingCustomer = customerDao.findById(customerId);
            if (existingCustomer.isEmpty()) {
                log.warn("Customer not found for update with ID: {}", customerId);
                throw new ModuleException(String.format(
                    messageSource.getMessage(CUSTOMER_NOT_FOUND, null, Locale.ENGLISH), customerId));
            }

            updateCustomerFields(existingCustomer.get(), customerEditDto);
            Customer updatedCustomer = customerDao.save(existingCustomer.get());
            log.info("Successfully updated customer with ID: {}", customerId);

            return new ResponseEntityDto(false, updatedCustomer);
        } catch (ModuleException e) {
            log.error("Failed to update customer: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error during customer update", e);
            throw new ModuleException("Unexpected error during customer update");
        } finally {
            MDC.clear();
        }
    }

    @Override
    @Transactional
    public ResponseEntityDto deleteCustomer(String id) {
        try {
            MDC.put("customerId", id);
            log.info("Attempting to delete customer with ID: {}", id);

            Customer customerToDelete = customerDao.findById(id)
                .orElseThrow(() -> {
                    log.warn("Customer not found for deletion with ID: {}", id);
                    return new IllegalArgumentException("Customer not found with id: " + id);
                });

            customerDao.delete(customerToDelete);
            log.info("Successfully deleted customer with ID: {}", id);

            return new ResponseEntityDto(false, customerToDelete);
        } catch (Exception e) {
            log.error("Error deleting customer with ID: {}", id, e);
            throw e;
        } finally {
            MDC.clear();
        }
    }

    @Override
    public ResponseEntityDto searchCustomers(String searchField, String searchTerm, int page, int size) {
        try {
            log.info("Searching customers with field: {}, term: {}, page: {}, size: {}", 
                    searchField, searchTerm, page, size);
            
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<Customer> searchResults = performSearch(searchField, searchTerm, pageRequest);
            
            log.debug("Found {} matching customers", searchResults.getTotalElements());
            return new ResponseEntityDto(false, searchResults);
        } catch (Exception e) {
            log.error("Error searching customers", e);
            throw new ModuleException("Error searching customers");
        }
    }

    // Private helper methods
    private void validateNewCustomer(CustomerCreateDto dto) {
        log.debug("Validating new customer data");
        
        Optional<Customer> checkCustomer = customerDao.findCustomerByEmail(dto.getEmail());
        if (checkCustomer.isPresent()) {
            log.warn("Email already in use: {}", dto.getEmail());
            throw new ModuleException(String.format(
                messageSource.getMessage(EMAIL_ALREADY_IN_USE, null, Locale.ENGLISH), 
                dto.getEmail()));
        }

        if (isAnyFieldNull(dto)) {
            log.warn("Required fields missing in customer creation request");
            throw new ModuleException(String.format(
                messageSource.getMessage(REQUEST_BODY_IS_MISSING_PAYLOAD, null, Locale.ENGLISH)));
        }
    }

    private boolean isAnyFieldNull(CustomerCreateDto dto) {
        return dto.getFirstName() == null ||
               dto.getLastName() == null ||
               dto.getEmail() == null ||
               dto.getPhoneNumber() == null ||
               dto.getGender() == null ||
               dto.getDob() == null;
    }

    private String generateCustomerId(Gender gender) {
        String timestamp = String.valueOf(System.currentTimeMillis()).substring(0, 4);
        String uuid = UUID.randomUUID().toString().substring(0, 5);
        String prefix = getGenderPrefix(gender);
        return String.format("%s-%s-%s", prefix, timestamp, uuid);
    }

    private String getGenderPrefix(Gender gender) {
        return switch (gender) {
            case MALE -> "M";
            case FEMALE -> "F";
            case NOT_SPECIFIED -> "N";
            case NON_BINARY -> "NB";
            case PREFER_NOT_TO_SAY -> "P";
            case OTHER -> "O";
        };
    }

    private void updateCustomerFields(Customer customer, CustomerEditDto dto) {
        log.debug("Updating customer fields");
        if (dto.getFirstName() != null) customer.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) customer.setLastName(dto.getLastName());
        if (dto.getEmail() != null) customer.setEmail(dto.getEmail());
        if (dto.getPhoneNumber() != null) customer.setPhoneNumber(dto.getPhoneNumber());
        if (dto.getGender() != null) customer.setGender(dto.getGender());
        if (dto.getDob() != null) customer.setDob(dto.getDob());
    }

    private Page<Customer> performSearch(String searchField, String searchTerm, PageRequest pageRequest) {
        return switch (searchField.toUpperCase()) {
            case "BOTH" -> customerDao
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                    searchTerm, searchTerm, searchTerm, pageRequest);
            case "NAME" -> customerDao
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                    searchTerm, searchTerm, pageRequest);
            case "EMAIL" -> customerDao
                .findByEmailContainingIgnoreCase(searchTerm, pageRequest);
            default -> customerDao
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                    searchTerm, searchTerm, searchTerm, pageRequest);
        };
    }

    private Customer customerCreateDtoToCustomer(CustomerCreateDto customerCreateDto) {
        log.debug("Converting CustomerCreateDto to Customer entity");
        
        Customer customer = new Customer();
        customer.setId(customerCreateDto.getId());
        customer.setGender(customerCreateDto.getGender());
        customer.setEmail(customerCreateDto.getEmail());
        customer.setFirstName(customerCreateDto.getFirstName());
        customer.setLastName(customerCreateDto.getLastName());
        customer.setDob(customerCreateDto.getDob());
        customer.setPhoneNumber(customerCreateDto.getPhoneNumber());
    
        log.trace("Customer conversion completed for email: {}", customerCreateDto.getEmail());
        return customer;
    }
}