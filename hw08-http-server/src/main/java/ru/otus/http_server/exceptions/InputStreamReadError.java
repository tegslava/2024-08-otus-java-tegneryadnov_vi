package ru.otus.http_server.exceptions;

public class InputStreamReadError extends RuntimeException {
    public InputStreamReadError(String message) {
        super(message);
    }
}
