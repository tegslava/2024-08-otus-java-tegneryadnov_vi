package ru.otus.java.pro.spring.app.exceptions_handling;

import ru.otus.java.pro.spring.app.entities.Account;

public record TransferParamsDto(Account sourceAccount, Account targetAccount, int amount) {}
