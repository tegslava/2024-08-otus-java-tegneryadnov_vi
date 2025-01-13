package ru.otus.java.pro.spring.app.exceptions_handling;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ErrorDto {
    private String code;
    private String message;
    private LocalDateTime dateTime;

    public ErrorDto(String code, String message) {
        this.code = code;
        this.message = message;
        this.dateTime = LocalDateTime.now();
    }
}
