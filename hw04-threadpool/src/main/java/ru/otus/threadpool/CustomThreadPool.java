package ru.otus.threadpool;

import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * Кастомная реализация пула потоков
 */
public class CustomThreadPool {
    private static final Logger logger = LoggerFactory.getLogger(CustomThreadPool.class);
    private static final int MIN_THREAD_POOL_SIZE = 1;
    private static final int MAX_THREAD_POOL_SIZE = 100;
    private final Thread[] arrThreads;
    private final BlockingQueue queue;
    private volatile boolean running = true;
    private final int threadPoolSize;

    /***
     *Принимает на вход размер пула порождаемых потоков
     *@param threadPoolSize размер пула. Диапазон значений от 1 .. 100
     */
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

    /***
     * Метод, порождающий и запускающий потоки-исполнители. В каждом потоке крутится
     * цикл ожидания задачи из очереди.
     * До получения задачи из очереди, потоки выставляются в WAITING.
     * Потоки получают имя по умолчанию "Поток N", где N порядковый номер потока
     * Пул потоков возвращается в виде массива
     * Цикл ожидания прекращается, после вызова shutdown().
     * @return Thread[] массив запущенных потоков.
     */
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

    /***
     * Метод помещает задачу в очередь исполнения для пула потоков.
     * После вызова shutdown(), метод не принимает новые задачи
     * @param task задача для исполнения
     */
    public void execute(Runnable task) {
        if (running) {
            queue.put(task);
        }
    }

    /***
     * Кастомная реализация блокирующей очереди на основе LinkedList<Runnable>
     * Метод put(Runnable task) помещает задачу в очередь, неблокирующий.
     * Метод get() извлекает задачу из очереди. Блокирующая операция
     */
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
            notifyAll();
        }
    }

    /***
     * Метод порождает задачу для теста. Возвращает объект класса имплементирующего Runnable.
     * @param taskNum входящий номер задачи, целое число.
     * @return объект класса имплементирующего Runnable
     */
    static Runnable getTask(int taskNum) {
        final int task_num = taskNum;
        return () -> {
            try {
                Thread.sleep(10);
                logger.info("Task {} finished", task_num);
            } catch (InterruptedException e) {
                logger.debug("Trying to stop {} Task {}", Thread.currentThread().getName(), task_num);
                Thread.currentThread().interrupt();
            }
        };
    }

    /***
     * Метод останавливающий работу пула потоков.
     * Запрещает принимать новые задачи в очередь, (running = false;)
     * У потоков пула вызывает InterruptedException
     */
    public void shutdown() {
        running = false;
        for (Thread thread : arrThreads) {
            if (!thread.isInterrupted()) thread.interrupt();
        }
        logger.debug("shutdown()");
    }

    /***
     * Тестируем запуск задач в потоках пула.
     * На заданном шаге (treshHold) цикла вызывается shutdown().
     * Убедимся, что потоки завершаются
     */
    public static void main(String[] args) throws InterruptedException {
        int threadNum = 10;
        int taskNum = 100;
        int treshHold = 5;
        CustomThreadPool pool = new CustomThreadPool(threadNum);
        for (int i = 0; i < taskNum; i++) {
            if (i == treshHold) {
                Thread.sleep(100);
                pool.shutdown();
            }
            pool.execute(getTask(i));
        }
        Thread.sleep(100);
        pool.showThreadsStates();
    }
}
