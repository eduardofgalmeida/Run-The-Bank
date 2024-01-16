package com.backend.core.service;

import com.backend.core.controller.AccountController;
import com.backend.core.entity.Account;
import com.backend.core.request.TransferRequest;
import com.backend.core.exception.DuplicateDocumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class AccountServiceTest {
    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateAccount() {
        Account account = new Account();
        account.setAgency("1234");
        account.setBalance(BigDecimal.ZERO);
        account.setActive(true);

        when(accountService.createAccount(account)).thenReturn(account);

        ResponseEntity<Account> response = accountController.createAccount(account);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(account, response.getBody());
    }

    @Test
    void testCreateAccount_Conflict() {
        Account account = new Account();
        account.setAgency("1234");
        account.setBalance(BigDecimal.ZERO);
        account.setActive(true);

        when(accountService.createAccount(account)).thenThrow(new DuplicateDocumentException("Duplicate document"));

        ResponseEntity<Account> response = accountController.createAccount(account);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetAllAccounts() {
        when(accountService.getAllAccounts()).thenReturn(List.of(new Account(), new Account()));

        ResponseEntity<List<Account>> response = accountController.getAllAccounts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testPerformTransfer_Success() {
        TransferRequest transferRequest = new TransferRequest("1", "2", BigDecimal.TEN);

        ResponseEntity<String> response = accountController.performTransfer(transferRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Transferência realizada com sucesso.", response.getBody());
    }
}