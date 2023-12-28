package com.demo.proclientmanager.repository;

import com.demo.proclientmanager.model.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class CustomerDaoTest {

    @Autowired
    private CustomerDao customerDao;

    @Test
    void testFindCustomerByEmail() {
        // Arrange
        Customer customer = new Customer();
        customer.setEmail("test@example.com");
        customerDao.save(customer);

        // Act
        Optional<Customer> result = customerDao.findCustomerByEmail("test@example.com");

        // Assert
        assertEquals(customer, result.orElse(null));
    }


    @Test
    void testFindByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase() {
        // Arrange
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");
        customerDao.save(customer);

        // Act
        Page<Customer> result = customerDao.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                "John", "Doe", "john.doe@example.com", PageRequest.of(0, 10));

        // Assert
        assertEquals(1, result.getTotalElements());
        assertEquals(customer, result.getContent().get(0));
    }

    @Test
    void testFindByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCase() {
        // Arrange
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.save(customer);

        // Act
        Page<Customer> result = customerDao.findByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCase(
                "John", "Doe", PageRequest.of(0, 10));

        // Assert
        assertEquals(1, result.getTotalElements());
        assertEquals(customer, result.getContent().get(0));
    }

    @Test
    void testFindByEmailContainingIgnoreCase() {
        // Arrange
        Customer customer = new Customer();
        customer.setEmail("test@example.com");
        customerDao.save(customer);

        // Act
        Page<Customer> result = customerDao.findByEmailContainingIgnoreCase("test@example.com", PageRequest.of(0, 10));

        // Assert
        assertEquals(1, result.getTotalElements());
        assertEquals(customer, result.getContent().get(0));
    }
}
