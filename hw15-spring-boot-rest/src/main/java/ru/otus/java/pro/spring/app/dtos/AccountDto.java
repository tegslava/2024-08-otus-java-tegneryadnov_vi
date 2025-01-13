package ru.otus.java.pro.spring.app.dtos;

public record AccountDto(String id, String clientId, String accountNumber, int balance, boolean isBlocked) {}
