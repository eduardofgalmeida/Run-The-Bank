package com.backend.core.controller;

import com.backend.core.entity.Account;
import com.backend.core.service.AccountService;
import com.backend.core.exception.DuplicateDocumentException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class AccountControllerTests {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @Test
    void testCreateAccount() throws DuplicateDocumentException {
        Account mockAccount = new Account();
        when(accountService.createAccount(any())).thenReturn(mockAccount);

        ResponseEntity<Account> response = accountController.createAccount(new Account());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockAccount, response.getBody());
    }

    @Test
    void testGetAccountByIdSuccess() throws AccountNotFoundException {
        Account account = new Account();
        account.setId(1L);
        account.setAgency("1234");
        account.setBalance(BigDecimal.TEN);
        account.setActive(true);

        when(accountService.getAccountByAgency("1")).thenReturn(account);

        ResponseEntity<Account> response = accountController.getAccountById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(account, response.getBody());
    }

    @Test
    void testGetAccountByIdNotFound() throws AccountNotFoundException {
        when(accountService.getAccountByAgency("1")).thenThrow(new AccountNotFoundException("Account not found"));

        ResponseEntity<Account> response = accountController.getAccountById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetAllAccounts() {
        when(accountService.getAllAccounts()).thenReturn(List.of(new Account(), new Account()));

        ResponseEntity<List<Account>> response = accountController.getAllAccounts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}