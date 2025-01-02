package ru.otus.testexceptions;

import java.lang.reflect.Method;

public class MethodAccessException extends Exception {
    public MethodAccessException(Method method, String errorMessage) {
        super(String.format("Error accessing method: %s. Error: %s", method.getName(), errorMessage));
    }
}
