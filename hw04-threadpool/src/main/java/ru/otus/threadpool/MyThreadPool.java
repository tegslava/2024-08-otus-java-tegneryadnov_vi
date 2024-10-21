package ru.otus.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import ru.otus.workerthread.WokerThread;

//import java.util.ArrayList;
import java.util.LinkedList;
//import java.util.concurrent.Executors;


public class MyThreadPool {
    private static final Logger logger = LoggerFactory.getLogger(MyThreadPool.class);
    private static final int MIN_POOL_SIZE = 0;
    private static final int MAX_POOL_SIZE = 100;
    private final Thread[] arrThreads;
    //private LinkedList<Runnable> queueTasks;
    private static final MyThreadPool.BlockingQueue queue = new MyThreadPool.BlockingQueue();
    //private final Object queueMonitor = new Object();


    public MyThreadPool(int threadPoolSize) {
        if (threadPoolSize < MIN_POOL_SIZE || threadPoolSize > MAX_POOL_SIZE) {
            throw new IllegalArgumentException("Invalid value threadPoolSize " + threadPoolSize);
        }
        arrThreads = new Thread[threadPoolSize];
        initialize();
        //Executors.newFixedThreadPool()
    }

    private void initialize() {
/*        for (int i = 0; i < arrThreads.length; i++) {
            Thread thread = new Thread(workingThread());
            arrThreads[i] = thread;
            thread.start();
        }*/
        int i;
        for (i = 0; i < arrThreads.length; i++) {
            Thread worker = new Thread(() -> {
                while (true) {
                    Runnable task = queue.get();
                    task.run();
                }
            });
            worker.setName("Поток "+ i);
            worker.start();
            arrThreads[i] = worker;
        }

        for(int j = 0; j<arrThreads.length; j++){
            logger.info("{} {}", arrThreads[j].getName(), arrThreads[j].getState());
        }

    }

/*    public void execute(Runnable task) {
        synchronized (queueMonitor) {
            queueTasks.add(task);
            notifyAll();
        }
    }*/

/*    private Runnable workingThread() {
        var thread_name = Thread.currentThread().getName();
        var thread_id = Thread.currentThread().threadId();
        logger.info("Поток {0} запущен с id:{1}", thread_name, thread_id);

    }*/
    static class BlockingQueue {
        LinkedList<Runnable> tasks = new LinkedList<>();

        public synchronized Runnable get() {
            while (tasks.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    logger.error("{}", e.getMessage());
                }
            }
            Runnable task = tasks.poll();
            return task;
        }

        public synchronized void put(Runnable task) {
            tasks.add(task);
            notify();
        }
    }

    public static Runnable getTask(int taskNum) {
        final int task_num = taskNum;
        return new Runnable() {
            @Override
            public void run() {
                logger.info("Task {} started", /*Thread.currentThread().getName()*/ taskNum);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                logger.info("Task {} finished",/*Thread.currentThread().getName()*/ taskNum);
            }
        };
    }

    public static void main(String[] args) {
        MyThreadPool myThreadPool = new MyThreadPool(3);
        for (int i = 0; i < 10; i++) {
            queue.put(getTask(i));
        }
    }
}
