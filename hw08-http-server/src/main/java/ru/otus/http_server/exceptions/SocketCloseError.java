package ru.otus.http_server.exceptions;

public class SocketCloseError extends RuntimeException {
    public SocketCloseError(String message) {
        super(message);
    }
}
