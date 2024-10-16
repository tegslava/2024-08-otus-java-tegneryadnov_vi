package ru.otus.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

public class MyThreadPool {
    private static final Logger logger = LoggerFactory.getLogger(MyThreadPool.class);
    private final int MIN_POOL_SIZE = 0;
    private final int MAX_POOL_SIZE = 100;
    private final List<Thread> threadPool;
    private final int threadPoolSize;

    public MyThreadPool(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
        if (threadPoolSize > MIN_POOL_SIZE && threadPoolSize <= MAX_POOL_SIZE) {
            threadPool = getFillingThreadList();
        } else {
            throw new IllegalArgumentException("Invalid value threadPoolSize " + threadPoolSize);
        }
    }

    private ArrayList<Thread> getFillingThreadList() {
        var threadList = new ArrayList<Thread>(threadPoolSize);
        for (int i = 0; i < threadPoolSize; i++) {
            threadList.add(new Thread());
        }
        return threadList;
    }

    public static void main(String[] args) {
        MyThreadPool myThreadlogger = new MyThreadPool(5);
        ThreadPoolExecutor
        //logger.info("{}", myThreadlogger.threadList.size());
        //myThreadlogger.threadList.forEach(x -> logger.info("{}", x.getName() + " " + x.threadId()));
    }
}
