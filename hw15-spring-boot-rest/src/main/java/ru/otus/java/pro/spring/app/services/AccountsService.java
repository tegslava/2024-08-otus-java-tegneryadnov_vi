package ru.otus.java.pro.spring.app.services;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.java.pro.spring.app.entities.Account;
import ru.otus.java.pro.spring.app.repositories.AccountsRepository;

@Service
@RequiredArgsConstructor
public class AccountsService {
    private final AccountsRepository accountsRepository;

    public Optional<Account> getAccountById(String id, String clientId) {
        return accountsRepository.findByIdAndClientId(id, clientId);
    }

    public List<Account> getAllAccounts(String clientId) {
        return accountsRepository.findAllByClientId(clientId);
    }

    public Optional<Account> getAccountByAccountNumber(String accountNumber, String clientId) {
        return accountsRepository.findByAccountNumberAndClientId(accountNumber, clientId);
    }
}
