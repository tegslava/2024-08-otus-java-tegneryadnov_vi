package ru.otus.testing;

import ru.otus.testing.task.Task;
import ru.otus.testing.task.TaskStatus;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamAPIExercise {
    /**
     * Список задач с заданным статусом
     * @param taskStream входящий поток задач
     * @param taskStatus определяющий статус
     * @return лист задач, отвечающих условию поиска
     */
    static List<Task> getTasksListWithStatus(Stream<Task> taskStream, TaskStatus taskStatus) {
        return taskStream.filter(t -> t.getTaskStatus() == taskStatus).toList();
    }

    /**
     * Количество задач с заданным статусом
     * @param taskStream входящий поток задач
     * @param taskStatus определяющий статус
     * @return значение типа long
     */
    static long getTaskCountWithStatus(Stream<Task> taskStream, TaskStatus taskStatus) {
        return taskStream.filter(t -> t.getTaskStatus() == taskStatus).count();
    }

    /**
     * Проверяет поток задач на соответствие условию
     * @param taskStream входящий поток задач
     * @param existsId ID задачи, которая должна быть
     * @param notExistsId ID задачи, которой не должно быть
     * @return результат в виде boolean
     */
    static boolean conditionIsMet(Stream<Task> taskStream, long existsId, long notExistsId) {
        var s = taskStream.map(Task::getId).collect(Collectors.toSet());
        return s.contains(existsId) && !s.contains(notExistsId);
    }

    /**
     * Список задач, отсортированных по статусу (Открыта, В работе, Закрыта)
     * @param taskStream входящий поток задач
     * @return отсортированный поток задач
     */
    static List<Task> getTasksListSortedByStatus(Stream<Task> taskStream) {
        Comparator<Task> taskComparator = Comparator.comparingInt(t -> t.getTaskStatus().ordinal());
        return taskStream.sorted(taskComparator).toList();
    }

    /**
     * Объединение сначала в группы по статусам, а потом (внутри каждой группы) в подгруппы четных и нечетных по ID
     * @param taskStream входящий поток задач
     * @return мапа с заданной группировкой
     */
    static Map<TaskStatus, Map<Boolean, List<Task>>> getTasksGroupByStatusIdEvenOdd(Stream<Task> taskStream) {
        return taskStream.collect(
                Collectors.groupingBy(Task::getTaskStatus,
                        Collectors.groupingBy(k -> (k.getId() % 2L) == 0)));
    }

    /**
     * Разбивка на две группы: с заданным статусом и остальное.
     * @param taskStream входящий поток задач
     * @param taskStatus статус отбора в группу
     * @param byStatus переключатель: true в группе задачи с заданным статусом,
     *                 false в группе задачи со статусом, отличным от заданного
     * @return мапа, с ключом byStatus и листом задач
     */
    static Map<Boolean, List<Task>> getTaskGroupByStatusAndOthers(Stream<Task> taskStream, TaskStatus taskStatus,
                                                                  boolean byStatus) {
        var resultMap = new HashMap<Boolean, List<Task>>();
        resultMap.put(byStatus, taskStream
                .collect(Collectors.partitioningBy(task -> task.getTaskStatus().equals(taskStatus))).get(byStatus));
        return resultMap;
    }
}

