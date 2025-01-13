package ru.otus.java.pro.spring.app.exceptions_handling;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationFieldErrorDto {
    private String field;
    private String message;
}
