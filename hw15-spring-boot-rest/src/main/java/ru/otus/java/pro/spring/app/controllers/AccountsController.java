package ru.otus.java.pro.spring.app.controllers;

import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.java.pro.spring.app.dtos.*;
import ru.otus.java.pro.spring.app.entities.Account;
import ru.otus.java.pro.spring.app.exceptions_handling.ResourceNotFoundException;
import ru.otus.java.pro.spring.app.services.AccountsService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountsController {
    private final AccountsService accountsService;

    private static final Function<Account, AccountDto> ACCOUNT_TO_DTO =
            a -> new AccountDto(a.getId(), a.getClientId(), a.getAccountNumber(), a.getBalance(), a.isBlocked());

    @GetMapping
    public AccountsPageDto getAllAccounts(@RequestHeader(name = "client-id") String clientId) {
        return new AccountsPageDto(accountsService.getAllAccounts(clientId).stream()
                .map(ACCOUNT_TO_DTO)
                .toList());
    }

    @GetMapping("/{id}")
    public AccountDto getAccountById(@RequestHeader(name = "client-id") String clientId, @PathVariable String id) {
        return ACCOUNT_TO_DTO.apply(accountsService
                .getAccountById(id, clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Счет не найден")));
    }
}
