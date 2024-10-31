package ru.otus.threadpool;

import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomThreadPool {
    private static final Logger logger = LoggerFactory.getLogger(CustomThreadPool.class);
    private static final int MIN_THREAD_POOL_SIZE = 1;
    private static final int MAX_THREAD_POOL_SIZE = 100;
    private final Thread[] arrThreads;
    private final BlockingQueue queue;
    private volatile boolean running = true;
    private final int threadPoolSize;

    public CustomThreadPool(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
        if (threadPoolSize < MIN_THREAD_POOL_SIZE || threadPoolSize > MAX_THREAD_POOL_SIZE) {
            throw new IllegalArgumentException("Invalid value threadPoolSize " + threadPoolSize);
        }
        queue = new CustomThreadPool.BlockingQueue();
        arrThreads = initialize();
    }

    public final void showThreadsStates() {
        for (var t : arrThreads) {
            logger.debug("{} {}", t.getName(), t.getState());
        }
    }

    private Thread[] initialize() {
        Thread[] arr = new Thread[threadPoolSize];
        for (int i = 0; i < threadPoolSize; i++) {
            Thread worker = new Thread(() -> {
                while (running) {
                    Runnable task = queue.get();
                    if (task != null) task.run();
                }
            });
            worker.setName("Поток " + i);
            arr[i] = worker;
            worker.start();
        }
        return arr;
    }

    public void execute(Runnable task) {
        if (running) {
            queue.put(task);
        }
    }

    private static final class BlockingQueue {
        LinkedList<Runnable> tasks = new LinkedList<>();

        synchronized Runnable get() {
            while (tasks.isEmpty() && !Thread.currentThread().isInterrupted()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return null;
                }
            }
            return tasks.poll();
        }

        synchronized void put(Runnable task) {
            tasks.add(task);
        }
    }

    static Runnable getTask(int taskNum) {
        final int task_num = taskNum;
        return () -> {
            try {
                Thread.sleep(100);
                logger.info("Task {} finished", task_num);
            } catch (InterruptedException e) {
                logger.debug("Trying to stop {} Task {}", Thread.currentThread().getName(), task_num);
                Thread.currentThread().interrupt();
            }
        };
    }

    public void shutdown() {
        running = false;
        for (Thread thread : arrThreads) {
            if (!thread.isInterrupted()) thread.interrupt();
        }
        logger.debug("shutdown()");
    }

    public static void main(String[] args) throws InterruptedException {
        int threadNum = 10;
        int taskNum = 100;
        int treshHold = 5;
        CustomThreadPool pool = new CustomThreadPool(threadNum);
        for (int i = 0; i < taskNum; i++) {
            if (i == treshHold) {
                Thread.sleep(1000);
                pool.shutdown();
            }
            pool.execute(getTask(i));
        }
        Thread.sleep(1);
        pool.showThreadsStates();
    }
}
