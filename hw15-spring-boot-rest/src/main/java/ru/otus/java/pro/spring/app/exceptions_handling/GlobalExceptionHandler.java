package ru.otus.java.pro.spring.app.exceptions_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<ErrorDto> catchResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(new ErrorDto("RESOURCE_NOT_FOUND", e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = BusinessLogicException.class)
    public ResponseEntity<ErrorDto> catchBusinessLogicException(BusinessLogicException e) {
        return new ResponseEntity<>(new ErrorDto("BUSINESS_LOGIC_ERROR", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ValidationBusinessLogicException.class)
    public ResponseEntity<ValidationErrorDto> catchValidationBusinessLogicException(
            ValidationBusinessLogicException e) {
        return new ResponseEntity<>(
                new ValidationErrorDto(
                        e.getCode(),
                        e.getMessage(),
                        e.getErrors().stream()
                                .map(ve -> new ValidationFieldErrorDto(ve.getField(), ve.getMessage()))
                                .toList()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ValidationException.class)
    public ResponseEntity<ValidationErrorDto> catchValidationException(ValidationException e) {
        return new ResponseEntity<>(
                new ValidationErrorDto(
                        e.getCode(),
                        e.getMessage(),
                        e.getErrors().stream()
                                .map(ve -> new ValidationFieldErrorDto(ve.getField(), ve.getMessage()))
                                .toList()),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
