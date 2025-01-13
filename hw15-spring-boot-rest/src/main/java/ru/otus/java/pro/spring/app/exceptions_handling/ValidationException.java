package ru.otus.java.pro.spring.app.exceptions_handling;

import java.util.List;
import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {
    private final String code;
    private final transient List<ValidationFieldError> errors;

    public ValidationException(String code, String message, List<ValidationFieldError> errors) {
        super(message);
        this.code = code;
        this.errors = errors;
    }
}
