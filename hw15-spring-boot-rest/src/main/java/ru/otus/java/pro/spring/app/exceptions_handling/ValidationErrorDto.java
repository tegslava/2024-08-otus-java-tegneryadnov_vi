package ru.otus.java.pro.spring.app.exceptions_handling;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class ValidationErrorDto {
    private String code;
    private String message;
    private List<ValidationFieldErrorDto> errors;
    private LocalDateTime dateTime;

    public ValidationErrorDto(String code, String message, List<ValidationFieldErrorDto> errors) {
        this.code = code;
        this.message = message;
        this.errors = errors;
        this.dateTime = LocalDateTime.now();
    }
}
