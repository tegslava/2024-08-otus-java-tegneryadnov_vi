package ru.otus.testexceptions;

import java.lang.reflect.Method;

public class MethodTestFailedException extends Exception {
    public MethodTestFailedException(Method method, String errorMessage) {
        super(String.format("Method test failed: %s. Error: %s", method.getName(), errorMessage));
    }
}
