package ru.otus.java.pro.spring.app.services;

import java.util.List;
import java.util.Optional;
import ru.otus.java.pro.spring.app.entities.Account;

public interface AccountsService {
    Optional<Account> getAccountById(String id, String clientId);

    List<Account> getAllAccounts(String clientId);

    Optional<Account> getAccountByAccountNumber(String accountNumber, String clientId);
}
