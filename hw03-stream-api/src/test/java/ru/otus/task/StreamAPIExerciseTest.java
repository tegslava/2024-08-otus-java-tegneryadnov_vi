package ru.otus.task;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamAPIExerciseTest {
    private static Stream<Task> taskStream;

    @BeforeEach
    void init() {
        taskStream = Stream.of(
                Task.builder()
                        .id(1)
                        .name("Задача 1 открыта")
                        .taskStatus(TaskStatus.NEW)
                        .build(),
                Task.builder()
                        .id(2)
                        .name("Задача 2 открыта")
                        .taskStatus(TaskStatus.NEW)
                        .build(),
                Task.builder()
                        .id(3)
                        .name("Задача 3 закрыта")
                        .taskStatus(TaskStatus.CLOSED)
                        .build(),
                Task.builder()
                        .id(4)
                        .name("Задача 4 в работе")
                        .taskStatus(TaskStatus.ACTIVE)
                        .build(),
                Task.builder()
                        .id(5)
                        .name("Задача 5 закрыта")
                        .taskStatus(TaskStatus.CLOSED)
                        .build(),
                Task.builder()
                        .id(6)
                        .name("Задача 6 в работе")
                        .taskStatus(TaskStatus.ACTIVE)
                        .build(),
                Task.builder()
                        .id(7)
                        .name("Задача 7 закрыта")
                        .taskStatus(TaskStatus.CLOSED)
                        .build(),
                Task.builder()
                        .id(8)
                        .name("Задача 8 закрыта")
                        .taskStatus(TaskStatus.CLOSED)
                        .build(),
                Task.builder()
                        .id(9)
                        .name("Задача 9 в работе")
                        .taskStatus(TaskStatus.ACTIVE)
                        .build()
        );
    }

    @Test
    @DisplayName("Список задач со статусом “В работе”")
    void tasksWithStatusACTIVE() {
        List<Task> filteredTaskList = taskStream.filter(x -> x.getTaskStatus() == TaskStatus.ACTIVE).toList();
        assertThat(filteredTaskList).hasSize(3);
        assertThat(filteredTaskList).contains("Задача 4 в работе");
        assertThat(filteredTaskList.contains("Задача 6 в работе"));
        assertThat(filteredTaskList.contains("Задача 9 в работе"));
    }

    @Test
    @DisplayName("Количество задач со статусом “Закрыта”")
    void tasksCountWithStatusCLOSED() {
        long cnt = taskStream.filter(x -> x.getTaskStatus() == TaskStatus.CLOSED).count();
        assertEquals(4, cnt);
    }

    @Test
    @DisplayName("Наличие задачи с ID = 2 и отсутствие с ID = 99")
    void conditionIsMet() {
        var s = taskStream.map(Task::getId).collect(Collectors.toSet());
        assertThat(s.contains(2L) & !s.contains(99L));
    }

    @Test
    @DisplayName("Список задач, отсортированных по статусу (Открыта, В работе, Закрыта)")
    void tasksListSortedByStatus() {
        Comparator<Task> taskComparator = Comparator.comparingInt(t -> t.getTaskStatus().ordinal());
        List<TaskStatus> statusList = taskStream.sorted(taskComparator).map(Task::getTaskStatus).toList();
        List<TaskStatus> expectedStatusList = List.of(
                TaskStatus.NEW, TaskStatus.NEW,
                TaskStatus.ACTIVE, TaskStatus.ACTIVE, TaskStatus.ACTIVE,
                TaskStatus.CLOSED, TaskStatus.CLOSED, TaskStatus.CLOSED, TaskStatus.CLOSED);
        assertEquals(expectedStatusList, statusList);
    }

    @Test
    @DisplayName("Объединение сначала в группы по статусам, а потом (внутри каждой группы) в подгруппы четных и нечетных по ID")
    void getTasksGroupByStatusIdEvenOdd() {
        var mapTasksList = taskStream.collect(
                Collectors.groupingBy(Task::getTaskStatus,
                        Collectors.groupingBy(k -> (k.getId() % 2L) == 0)));
        assertThat(mapTasksList.size()==3);
        Set<TaskStatus> expectedSetKey = Set.of(TaskStatus.NEW,TaskStatus.ACTIVE,TaskStatus.CLOSED);
        assertThat(expectedSetKey).isEqualTo(mapTasksList.keySet());
        assertThat(List.of(1L)).isEqualTo(mapTasksList.get(TaskStatus.NEW).get(false).stream().map(Task::getId).toList());
        assertThat(List.of(2L)).isEqualTo(mapTasksList.get(TaskStatus.NEW).get(true).stream().map(Task::getId).toList());
        assertThat(List.of(9L)).isEqualTo(mapTasksList.get(TaskStatus.ACTIVE).get(false).stream().map(Task::getId).toList());
        assertThat(List.of(4L,6L)).isEqualTo(mapTasksList.get(TaskStatus.ACTIVE).get(true).stream().map(Task::getId).toList());
        assertThat(List.of(3L,5L,7L)).isEqualTo(mapTasksList.get(TaskStatus.CLOSED).get(false).stream().map(Task::getId).toList());
        assertThat(List.of(8L)).isEqualTo(mapTasksList.get(TaskStatus.CLOSED).get(true).stream().map(Task::getId).toList());
    }

    @Test
    @Display
}
