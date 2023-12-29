package com.demo.proclientmanager.service;

import com.demo.proclientmanager.common.types.Gender;
import com.demo.proclientmanager.exception.ModuleException;
import com.demo.proclientmanager.model.Customer;
import com.demo.proclientmanager.payload.common.Acknowledgement;
import com.demo.proclientmanager.payload.common.ResponseEntityDto;
import com.demo.proclientmanager.payload.dto.CustomerCreateDto;
import com.demo.proclientmanager.payload.dto.CustomerEditDto;
import com.demo.proclientmanager.repository.CustomerDao;
import com.demo.proclientmanager.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerDao customerDao;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private CustomerCreateDto sampleCreateDto;
    private CustomerEditDto sampleEditDto;

    @BeforeEach
    void setUp() {
        sampleCreateDto = createSampleCustomerCreateDto();
        sampleEditDto = createSampleCustomerEditDto();
    }

    @Test
    void testCreateCustomer_Success() {
        // Arrange
        when(customerDao.findCustomerByEmail(anyString())).thenReturn(Optional.empty());
        when(customerDao.save(any())).thenReturn(createSampleCustomer());

        // Act
        ResponseEntityDto response = customerService.createCustomer(sampleCreateDto);

        // Assert
        assertEquals("successful", response.getStatus());
        assertNotNull(response.getResults());
        assertEquals(1, response.getResults().size());
        assertTrue(response.getResults().get(0) instanceof Customer);
    }


    @Test
    void testEditCustomer_Success() {
        // Arrange
        when(customerDao.findById(anyString())).thenReturn(Optional.of(createSampleCustomer()));
        when(customerDao.save(any())).thenReturn(createSampleCustomer());

        // Act
        ResponseEntityDto response = customerService.editCustomer(sampleEditDto);

        // Assert
        assertEquals("successful", response.getStatus());
        assertNotNull(response.getResults());
        assertEquals(1, response.getResults().size());
        assertTrue(response.getResults().get(0) instanceof Customer);
    }


    @Test
    void testDeleteCustomer_Success() {
        // Arrange
        String customerId = "123";
        when(customerDao.findById(anyString())).thenReturn(Optional.of(createSampleCustomer()));

        // Act
        ResponseEntityDto response = customerService.deleteCustomer(customerId);

        // Assert
        assertEquals("successful", response.getStatus());
        assertNotNull(response.getResults());
        assertEquals(1, response.getResults().size());
        assertTrue(response.getResults().get(0) instanceof Customer);
    }

    @Test
    void testDeleteCustomer_CustomerNotFound() {
        // Arrange
        String customerId = "123";
        when(customerDao.findById(anyString())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> customerService.deleteCustomer(customerId));
    }

    @Test
    void testSearchCustomers_Success() {
        // Arrange
        int page = 0;
        int size = 10;
        String searchField = "BOTH";
        String searchTerm = "John";
        when(customerDao.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(anyString(), anyString(), anyString(), any(PageRequest.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(createSampleCustomer())));

        // Act
        ResponseEntityDto response = customerService.searchCustomers(searchField, searchTerm, page, size);

        // Assert
        assertEquals("successful", response.getStatus());
        assertNotNull(response.getResults());
        assertEquals(1, response.getResults().size());
    }

    public static CustomerCreateDto createSampleCustomerCreateDto() {
        CustomerCreateDto customerCreateDto = new CustomerCreateDto();
        customerCreateDto.setId("sampleId");
        customerCreateDto.setFirstName("John");
        customerCreateDto.setLastName("Doe");
        customerCreateDto.setEmail("john.doe@example.com");
        customerCreateDto.setPhoneNumber("1234567890");
        customerCreateDto.setGender(Gender.MALE);
        customerCreateDto.setDob(new Date());
        return customerCreateDto;
    }

    public static CustomerEditDto createSampleCustomerEditDto() {
        CustomerEditDto customerEditDto = new CustomerEditDto();
        customerEditDto.setId("sampleId");
        customerEditDto.setFirstName("UpdatedJohn");
        customerEditDto.setLastName("UpdatedDoe");
        customerEditDto.setEmail("updated.john.doe@example.com");
        customerEditDto.setPhoneNumber("9876543210");
        customerEditDto.setGender(Gender.FEMALE);
        customerEditDto.setDob(new Date());
        return customerEditDto;
    }

    public static Customer createSampleCustomer() {
        Customer customer = new Customer();
        customer.setId("sampleId");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");
        customer.setPhoneNumber("1234567890");
        customer.setGender(Gender.MALE);
        customer.setDob(new Date());
        return customer;
    }
}
