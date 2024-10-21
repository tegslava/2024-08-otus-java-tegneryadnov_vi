package ru.otus.workerthread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.threadpool.MyThreadPool;

import java.util.ArrayList;

public class WokerThread {
    private static final Logger logger = LoggerFactory.getLogger(WokerThread.class);
    public static void main(String[] args) {
        BlockingQueue queue = new BlockingQueue();
        Thread worker = new Thread(() -> {
            while (true) {
                Runnable task = queue.get();
                task.run();
            }
        });
        worker.start();
        for (int i = 0; i < 10; i++) {
            queue.put(getTask(i));
        }
    }

    public static Runnable getTask(int taskNum) {
        final int task_num = taskNum;
        return new Runnable() {
            @Override
            public void run() {
                logger.info("Task {} started",taskNum);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                logger.info("Task {} finished",/*this.hashCode()*/taskNum);
            }
        };
    }

    static class BlockingQueue {
        ArrayList<Runnable> tasks = new ArrayList<>();

        public synchronized Runnable get() {
            while (tasks.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Runnable task = tasks.get(0);
            tasks.remove(task);
            return task;
        }

        public synchronized void put(Runnable task) {
            tasks.add(task);
            notify();
        }
    }
}
