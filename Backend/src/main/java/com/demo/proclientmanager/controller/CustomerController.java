package com.demo.proclientmanager.controller;


import com.demo.proclientmanager.payload.common.ResponseEntityDto;
import com.demo.proclientmanager.payload.dto.CustomerCreateDto;
import com.demo.proclientmanager.payload.dto.CustomerEditDto;
import com.demo.proclientmanager.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/customer")
@AllArgsConstructor
@CrossOrigin
public class CustomerController {

    @NonNull
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<ResponseEntityDto> createCustomer(@RequestBody CustomerCreateDto customerCreateDto) {
        ResponseEntityDto response = customerService.createCustomer(customerCreateDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResponseEntityDto> getAllCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        ResponseEntityDto response = customerService.getCustomers(page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<ResponseEntityDto> getOneCustomer(@PathVariable String customerId) {
        ResponseEntityDto response = customerService.getOneCustomer(customerId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseEntityDto> searchCustomers(
            @RequestParam String searchField,
            @RequestParam String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        ResponseEntityDto response = customerService.searchCustomers(searchField ,searchTerm, page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<ResponseEntityDto> updateCustomer(@RequestBody CustomerEditDto customerEditDto) {
        ResponseEntityDto response = customerService.editCustomer(customerEditDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<ResponseEntityDto> deleteCustomer(@PathVariable String customerId) {
        ResponseEntityDto response = customerService.deleteCustomer(customerId);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}

