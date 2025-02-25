package ru.otus.java.pro.dtos;

import java.util.UUID;

public record MessageDto(UUID uuid, String messageText) {}
