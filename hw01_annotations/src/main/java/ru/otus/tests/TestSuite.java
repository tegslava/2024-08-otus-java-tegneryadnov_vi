package ru.otus.tests;
import ru.otus.annotations.*;

public class TestSuite {
    @BeforeSuite
    private static void init() {
        System.out.println("init");
    }

    @Test(priority = 1)
    public static void test1() {
        System.out.println("test1\tpriority = 1");
    }

    @Test(priority = 6)
    private static void test2() {
        System.out.println("test2\tpriority = 6");
    }

    @Test
    public static void test3() {
        System.out.println("test3\tpriority = 5");
    }

    @Test(priority = 6)
    public static void test4() {
        System.out.println("test4\tpriority = 6");
    }

    @Test(priority = 10)
    public static void test5() {
        System.out.println("test5\tpriority = 10");
    }

    @Test(priority = 3)
    public static void test6() {
        System.out.println("test6\tpriority = 3");
    }

    @Disabled
    @Test(priority = 4)
    public static void test7() {
        System.out.println("test7\tpriority = 4");
    }

    @Disabled
    @AfterSuite
    public static void test10() {
        System.out.println("finished");
    }
}
