package ru.otus;

import ru.otus.tests.TestSuite;
import ru.otus.tests.TestSuiteDisabled;

public class Application {
    public static void main(String[] args) {
            TestRunner.run(TestSuite.class);
            TestRunner.run(TestSuiteDisabled.class);
    }
}
