package com.backend.core.controller;

import com.backend.core.entity.Customer;
import com.backend.core.exception.CustomerNotFoundException;
import com.backend.core.exception.DuplicateDocumentException;
import com.backend.core.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/all")
    public ResponseEntity<Object> getAllAccounts() {
        List<Customer> allCustomer = customerService.getAllCustomer();
        if (allCustomer.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(allCustomer);
        }
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        try {
            Customer createdCustomer = customerService.createCustomer(customer);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
        } catch (DuplicateDocumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Optional<Customer>> getCustomerById(@PathVariable Long customerId) {
        try {
            Optional<Customer> customer = Optional.ofNullable(customerService.getCustomerById(customerId));
            return ResponseEntity.ok(customer);
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}