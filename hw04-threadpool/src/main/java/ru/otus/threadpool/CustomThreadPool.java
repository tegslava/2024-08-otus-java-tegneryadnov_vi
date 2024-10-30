package ru.otus.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import ru.otus.workerthread.WokerThread;

//import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//import java.util.concurrent.Executors;


public class CustomThreadPool {
    private static final Logger logger = LoggerFactory.getLogger(CustomThreadPool.class);
    private static final int MIN_THREAD_POOL_SIZE = 1;
    private static final int MAX_THREAD_POOL_SIZE = 100;
    private final Thread[] arrThreads;
    private static final BlockingQueue queue = new CustomThreadPool.BlockingQueue();
    private volatile static boolean running = true;
    private volatile static boolean terminated;

    public CustomThreadPool(int threadPoolSize) {
        if (threadPoolSize < MIN_THREAD_POOL_SIZE || threadPoolSize > MAX_THREAD_POOL_SIZE) {
            throw new IllegalArgumentException("Invalid value threadPoolSize " + threadPoolSize);
        }
        arrThreads = new Thread[threadPoolSize];
        initialize(threadPoolSize);
        //ExecutorService pool = Executors.newFixedThreadPool(10);
    }

    private void initialize(int threadNum) {
        for (int i = 0; i < threadNum; i++) {
            int idx = i;
            Thread worker = new Thread(() -> {
                while (running) {
                    Runnable task = queue.get();
                    task.run();
                }
            });
            worker.setName("Поток " + idx);
            arrThreads[i] = worker;
            worker.start();
        }
    }

    public void execute(Runnable task) {
        queue.put(task);
    }

    private final static class BlockingQueue {
        LinkedList<Runnable> tasks = new LinkedList<>();

        synchronized Runnable get() {
            while (tasks.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    //logger.error(" isEmpty {}", e.getMessage());
                }
            }
            Runnable task = tasks.poll();
            return task;
        }

        synchronized void put(Runnable task) {
            tasks.add(task);
            notify();
        }
    }

    static Runnable getTask(int taskNum, CountDownLatch latch) {
        final int task_num = taskNum;
        return new Runnable() {
            @Override
            public void run() {
                //logger.debug("Task {} started", taskNum);
                try {
                    Thread.sleep(1000);
                    //latch.countDown();
                } catch (InterruptedException e) {
                    logger.info(
                            "Trying to stop {} interrupt = {}", taskNum,
                            Thread.currentThread().isInterrupted());
                    // восстанавливаем флаг isInterrupted()
                    Thread.currentThread().interrupt();
                }
                logger.debug("Task {} finished", taskNum);
            }
        };
    }

    public void shutdown() {
        terminated = true;
        running = false;
/*
        for (Thread thread : arrThreads) {
            //thread.notify();
            thread.interrupt();
        }
*/

    }

    public boolean isTerminated() {
        return terminated;
    }

    public static void main(String[] args) {
        int taskNum = 100;
        int treshHold = 5;
        CustomThreadPool customThreadPool = new CustomThreadPool(10);
        //CountDownLatch latch = new CountDownLatch(treshHold);
        for (int i = 0; i < taskNum; i++) {
            try {
                customThreadPool.execute(getTask(i, null));
                if(i== treshHold) {customThreadPool.shutdown();}
                //latch.countDown();
            } catch (Exception e) {
                logger.error("for ... {}", e.getMessage());
                throw new RuntimeException(e);
            }
        }
        try {
            //latch.await();
            //customThreadPool.shutdown();
            Thread.sleep(2000);
            logger.info("DONE");
        } catch (InterruptedException e) {
            logger.error("fff {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
