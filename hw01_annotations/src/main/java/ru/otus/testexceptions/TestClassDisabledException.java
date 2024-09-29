package ru.otus.testexceptions;

public class TestClassDisabledException extends Exception {
    public TestClassDisabledException(Class<?> clazz, String errorMessage) {
        super(String.format("Test %s disabled with reason: %s", clazz.getName(), errorMessage));
    }
}
