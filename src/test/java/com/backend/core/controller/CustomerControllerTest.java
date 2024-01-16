package com.backend.core.controller;

import com.backend.core.entity.Customer;
import com.backend.core.service.CustomerService;
import com.backend.core.exception.CustomerNotFoundException;
import com.backend.core.exception.DuplicateDocumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllCustomersSuccess() {
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setName("Teste 1");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setName("Teste 2");

        List<Customer> customers = Arrays.asList(customer1, customer2);

        when(customerService.getAllCustomer()).thenReturn(customers);

        ResponseEntity<Object> response = customerController.getAllAccounts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customers, response.getBody());
    }

    @Test
    void testGetAllCustomersNoContent() {
        when(customerService.getAllCustomer()).thenReturn(Arrays.asList());

        ResponseEntity<Object> response = customerController.getAllAccounts();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testCreateCustomerSuccess() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Teste");

        when(customerService.createCustomer(customer)).thenReturn(customer);

        ResponseEntity<Customer> response = customerController.createCustomer(customer);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(customer, response.getBody());
    }

    @Test
    void testCreateCustomerConflict() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Teste");

        when(customerService.createCustomer(customer)).thenThrow(new DuplicateDocumentException("Duplicate document"));

        ResponseEntity<Customer> response = customerController.createCustomer(customer);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetCustomerByIdSuccess() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Teste");

        when(customerService.getCustomerById(1L)).thenReturn(customer);

        ResponseEntity<Optional<Customer>> response = customerController.getCustomerById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Optional.of(customer), response.getBody());
    }

    @Test
    void testGetCustomerByIdNotFound() {
        when(customerService.getCustomerById(1L)).thenThrow(new CustomerNotFoundException("Customer not found"));

        ResponseEntity<Optional<Customer>> response = customerController.getCustomerById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
}