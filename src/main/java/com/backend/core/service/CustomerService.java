package com.backend.core.service;

import com.backend.core.entity.Customer;
import com.backend.core.repository.CustomerRepository;
import com.backend.core.exception.CustomerNotFoundException;
import com.backend.core.exception.DuplicateDocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    public Customer createCustomer(Customer customer) {
        validateCustomerCreation(customer);
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Cliente não encontrado com o ID: " + customerId));
    }

    private void validateCustomerCreation(Customer customer) {
        validateUniqueDocument(customer.getDocument());
    }

    private void validateUniqueDocument(String document) {
        if (customerRepository.existsByDocument(document)) {
            throw new DuplicateDocumentException("Já existe um cliente associado a este documento.");
        }
    }
}