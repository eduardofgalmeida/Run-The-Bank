package com.backend.core.controller;

import com.backend.core.entity.Account;
import com.backend.core.exception.DuplicateDocumentException;
import com.backend.core.exception.InactiveAccountException;
import com.backend.core.exception.InsufficientBalanceException;
import com.backend.core.request.TransferRequest;
import com.backend.core.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/all")
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> allAccounts = accountService.getAllAccounts();
        if (allAccounts.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(allAccounts);
        }
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        try {
            Account createdAccount = accountService.createAccount(account);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
        } catch (DuplicateDocumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long accountAgency) {
        try {
            Account account = accountService.getAccountByAgency(String.valueOf(accountAgency));
            return ResponseEntity.ok(account);
        } catch (AccountNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> performTransfer(@RequestBody TransferRequest transferRequest) {
        try {
            accountService.performTransfer(transferRequest.getSourceAccountAgency(),
                    transferRequest.getTargetAccountAgency(),
                    transferRequest.getAmount());
            return ResponseEntity.ok("Transferência realizada com sucesso.");
        } catch (InsufficientBalanceException | InactiveAccountException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro durante a transferência.");
        }
    }
}
