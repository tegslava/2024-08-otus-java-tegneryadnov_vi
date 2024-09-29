package ru.otus.tests;
import ru.otus.annotations.*;
import ru.otus.priority.TestPriority;

public class TestSuite {
    @BeforeSuite
    private static void init() {
        System.out.println("init");
    }

    @Test(priority = TestPriority.ONE)
    public static void test1() {
        System.out.println("test1\tpriority = 1");
    }

    @Test(priority = TestPriority.SIX)
    private static void test2() {
        System.out.println("test2\tpriority = 6");
    }

    @Test
    public static void test3() {
        System.out.println("test3\tpriority = 5");
    }

    @Test(priority = TestPriority.SIX)
    public static void test4() {
        System.out.println("test4\tpriority = 6");
    }

    @Test(priority = TestPriority.TEN)
    public static void test5() {
        System.out.println("test5\tpriority = 10");
    }

    @Test(priority = TestPriority.THREE)
    public static void test6() {
        System.out.println("test6\tpriority = 3");
    }

    @Disabled
    @Test(priority = TestPriority.FOUR)
    public static void test7() {
        System.out.println("test7\tpriority = 4");
    }

    @Disabled
    @AfterSuite
    public static void test10() {
        System.out.println("finished");
    }
}
