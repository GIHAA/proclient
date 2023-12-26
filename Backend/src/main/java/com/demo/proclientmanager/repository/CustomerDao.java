package com.demo.proclientmanager.repository;

import com.demo.proclientmanager.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CustomerDao extends MongoRepository <Customer , String> {

    Optional<Customer> findCustomerByEmail(String email);
}
