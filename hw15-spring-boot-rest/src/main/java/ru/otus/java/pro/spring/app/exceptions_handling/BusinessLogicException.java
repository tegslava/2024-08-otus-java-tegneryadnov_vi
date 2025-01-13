package ru.otus.java.pro.spring.app.exceptions_handling;

import lombok.Getter;

@Getter
public class BusinessLogicException extends RuntimeException {
    private final String code;

    public BusinessLogicException(String message, String code) {
        super(message);
        this.code = code;
    }
}
