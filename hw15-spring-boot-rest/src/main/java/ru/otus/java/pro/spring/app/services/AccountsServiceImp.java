package ru.otus.java.pro.spring.app.services;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.java.pro.spring.app.entities.Account;
import ru.otus.java.pro.spring.app.repositories.AccountsRepository;

@Service
@RequiredArgsConstructor
public class AccountsServiceImp implements AccountsService {
    private final AccountsRepository accountsRepository;

    @Override
    public Optional<Account> getAccountById(String id, String clientId) {
        return accountsRepository.findByIdAndClientId(id, clientId);
    }

    @Override
    public List<Account> getAllAccounts(String clientId) {
        return accountsRepository.findAllByClientId(clientId);
    }

    @Override
    public Optional<Account> getAccountByAccountNumber(String accountNumber, String clientId) {
        return accountsRepository.findByAccountNumberAndClientId(accountNumber, clientId);
    }
}
