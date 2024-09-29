package ru.otus;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import ru.otus.annotations.*;
import ru.otus.testexceptions.MethodTestFailedException;
import ru.otus.testexceptions.TestClassDisabledException;

public class TestRunner {
    private static int disabledMethod;
    private static int methodTestPassed;
    private static int methodTestFailed;
    private static Class<?> clazz;

    public static void run(Class<?> testSuiteClass) {
        clazz = testSuiteClass;
        try {
            classDisabledCheck();
            var beforeSuiteMethods = getAnnotatedMethods(BeforeSuite.class);
            var afterSuiteMethods = getAnnotatedMethods(AfterSuite.class);
            var testSuiteMethods = getAnnotatedMethods(Test.class);
            classMarkupCheck(beforeSuiteMethods, afterSuiteMethods);
            execMethods(beforeSuiteMethods, "Test initialization error: ");
            testingMethods(testSuiteMethods);
            execMethods(afterSuiteMethods, "Test finalization error: ");
            showTestsStatistic();
        } catch (TestClassDisabledException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Выполняет методы инициализации и финализации тестов
     * Допускает выполнения приватных методов
     * Если есть дополнительная аннотоция @ Disabled, метод не выполняется
     *
     * @param methods массив методов
     */
    private static void execMethods(Method[] methods, String message) {
        for (var m : methods) {
            if (!m.isAnnotationPresent(Disabled.class)) {
                try {
                    if (m.trySetAccessible()) m.invoke(null);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(message + e.getMessage());
                }
            } else {
                disabledMethod++;
                System.out.println(m.getName() + "\t" + m.getAnnotation(Disabled.class).reason());
            }
        }
    }

    /**
     * Выполняет публичные методы, помеченные @Test
     * Порядок выполнения от большего @Test(priority к меньшему
     * Если есть дополнительная аннотоция @ Disabled, метод не выполняется
     *
     * @param testSuiteMethods массив методов
     */
    private static void testingMethods(Method[] testSuiteMethods) {
        Comparator<Method> comparator = Comparator.comparingInt((Method a) -> a.getAnnotation(Test.class).priority().ordinal()).reversed();
        for (var m : Arrays.stream(testSuiteMethods).sorted(comparator).toList()) {
            if (!m.isAnnotationPresent(Disabled.class)) {
                try {
                    try {
                        m.invoke(null);
                        methodTestPassed++;
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new MethodTestFailedException(m, e.getMessage());
                    }
                } catch (MethodTestFailedException ef) {
                    methodTestFailed++;
                    System.out.println(ef.getMessage());
                }
            } else {
                disabledMethod++;
                System.out.println(m.getName() + "\t" + m.getAnnotation(Disabled.class).reason());
            }
        }
    }

    /**
     * Проверяет, помечен ли класс аннтоацией @Disabled
     *
     */
    private static void classDisabledCheck() throws TestClassDisabledException {
        var annotationClassDisabled = Optional.ofNullable(clazz.getAnnotation(Disabled.class));
        if (annotationClassDisabled.isPresent()) {
            throw new TestClassDisabledException(clazz, annotationClassDisabled.get().reason());
        }
    }

    /**
     * Проверяет разметку класса аннотациями, согласно правилам:
     * - @BeforeSuite может быть в классе максимум 1
     * - @AfterSuite может быть в классе максимум 1
     * Аннотации @Test и @BeforeSuite/@AfterSuite не могут встречаться на одном и том же методе
     * Если класс с тестами криво размечен, бросает исключение
     *
     * @param beforeSuiteMethods массив метолов
     * @param afterSuiteMethods  массив метолов
     */
    private static void classMarkupCheck(Method[] beforeSuiteMethods, Method[] afterSuiteMethods) {
        if (beforeSuiteMethods.length > 1) {
            throw new IllegalArgumentException("BeforeSuite annotations more than 1");
        }
        if (afterSuiteMethods.length > 1) {
            throw new IllegalArgumentException("AfterSuite annotations more than 1");
        }
        if (existCrossAnnotatedMethods(Test.class, BeforeSuite.class) ||
                existCrossAnnotatedMethods(Test.class, AfterSuite.class) ||
                existCrossAnnotatedMethods(BeforeSuite.class, AfterSuite.class)) {
            throw new IllegalArgumentException("Invalid annotation intersection");
        }
    }

    /**
     * Проверяет наличие у метода двух аннотаций
     *
     * @param annotation1 первая аннтотация
     * @param annotation2 вторая аннотация
     * @return booolean
     */
    private static boolean existCrossAnnotatedMethods(Class<? extends Annotation> annotation1, Class<? extends Annotation> annotation2) {
        return Arrays.stream(clazz.getDeclaredMethods()).anyMatch(x -> x.isAnnotationPresent(annotation1) && x.isAnnotationPresent(annotation2));
    }

    /**
     * Возвращает массив методов, с заданной аннтоацией
     *
     * @param annotation искомая аннтотация
     * @return массив методов
     */
    private static Method[] getAnnotatedMethods(Class<? extends Annotation> annotation) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(x -> x.isAnnotationPresent(annotation))
                .toArray(Method[]::new);
    }

    /**
     * Выводит статистику тестирования
     */
    public static void showTestsStatistic() {
        System.out.printf("""

                Method testing statistics for %s
                  - tests passed %d;
                  - tests failed %d;
                  - tests disabled %d;

                """, clazz.getName(), methodTestPassed, methodTestFailed, disabledMethod);
    }
}
