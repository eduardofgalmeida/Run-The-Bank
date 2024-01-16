package com.backend.core.service;

import com.backend.core.entity.Account;
import com.backend.core.repository.AccountRepository;
import com.backend.core.exception.DuplicateDocumentException;
import com.backend.core.exception.InactiveAccountException;
import com.backend.core.exception.InsufficientBalanceException;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CamelContext camelContext;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account createAccount(Account account) {
        validateAccountCreation(account);
        initializeAccount(account);
        return accountRepository.save(account);
    }

    public Account getAccountByAgency(String accountAgency) throws AccountNotFoundException {
        return accountRepository.findByAgency(accountAgency)
                .orElseThrow(() -> new AccountNotFoundException("Agencia não encontrada: " + accountAgency));
    }

    public void performTransfer(String sourceAccountAgency, String targetAccountAgency, BigDecimal amount) throws AccountNotFoundException {
        Account sourceAccount = getAccountByAgency(sourceAccountAgency);
        Account targetAccount = getAccountByAgency(targetAccountAgency);

        validateTransferParameters(sourceAccount, targetAccount, amount);
        validateSufficientBalance(sourceAccount, amount);

        executeTransfer(sourceAccount, targetAccount, amount);
    }

    private void validateAccountCreation(Account account) {
        validateUniqueDocument(account.getAgency());
    }

    private void initializeAccount(Account account) {
        account.setActive(true);
    }

    private void validateTransferParameters(Account sourceAccount, Account targetAccount, BigDecimal amount) {
        validateActiveAccounts(sourceAccount, targetAccount);
        validatePositiveAmount(amount);
    }

    private void validateSufficientBalance(Account sourceAccount, BigDecimal amount) {
        BigDecimal sourceBalance = sourceAccount.getBalance();
        if (sourceBalance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException("A conta de origem não possui saldo suficiente para a transferência.");
        }
    }

    private void validateActiveAccounts(Account sourceAccount, Account targetAccount) {
        if (!sourceAccount.isActive() || !targetAccount.isActive()) {
            throw new InactiveAccountException("Uma ou ambas as contas estão inativas. A transferência não pode ser concluída.");
        }
    }

    private void validatePositiveAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da transferência deve ser maior que zero.");
        }
    }

    private void validateUniqueDocument(String agency) {
        Optional<Account> existingAccount = accountRepository.findByAgency(agency);
        if (existingAccount.isPresent()) {
            throw new DuplicateDocumentException("Já existe uma conta associada a este documento.");
        }
    }

    private void executeTransfer(Account sourceAccount, Account targetAccount, BigDecimal amount) {
        sourceAccount.setBalance(sourceAccount.getBalance().subtract(amount));
        targetAccount.setBalance(targetAccount.getBalance().add(amount));

        accountRepository.save(sourceAccount);
        accountRepository.save(targetAccount);

    //    camelContext.createProducerTemplate().sendBody("direct:paymentNotification", "Transferência realizada com sucesso.");
    }

}