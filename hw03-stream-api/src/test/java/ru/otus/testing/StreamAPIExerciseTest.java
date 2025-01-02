package ru.otus.testing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.testing.task.Task;
import ru.otus.testing.task.TaskStatus;

class StreamAPIExerciseTest {
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
                        .build());
    }

    @Test
    @DisplayName("Список задач со статусом “В работе”")
    void tasksWithStatusACTIVE() {
        var activeTaskList = StreamAPIExercise.getTasksListWithStatus(taskStream, TaskStatus.ACTIVE);
        var testedSet = activeTaskList.stream().map(Task::getTaskStatus).collect(Collectors.toSet());
        assertEquals(Set.of(TaskStatus.ACTIVE), testedSet);
    }

    @Test
    @DisplayName("Количество задач со статусом “Закрыта”")
    void tasksCountWithStatusCLOSED() {
        long cnt = StreamAPIExercise.getTaskCountWithStatus(taskStream, TaskStatus.CLOSED);
        assertEquals(4, cnt);
    }

    @Test
    @DisplayName("Наличие задачи с ID = 2 и отсутствие с ID = 99")
    void conditionIsMet() {
        assertThat(StreamAPIExercise.conditionIsMet(taskStream, 2L, 99L)).isTrue();
    }

    @Test
    @DisplayName("Список задач, отсортированных по статусу (Открыта, В работе, Закрыта)")
    void tasksListSortedByStatus() {
        var statusTaskList = StreamAPIExercise.getTasksListSortedByStatus(taskStream).stream()
                .map(Task::getTaskStatus)
                .toList();
        var expectedStatusTaskList = List.of(
                TaskStatus.NEW,
                TaskStatus.NEW,
                TaskStatus.ACTIVE,
                TaskStatus.ACTIVE,
                TaskStatus.ACTIVE,
                TaskStatus.CLOSED,
                TaskStatus.CLOSED,
                TaskStatus.CLOSED,
                TaskStatus.CLOSED);
        assertEquals(expectedStatusTaskList, statusTaskList);
    }

    @Test
    @DisplayName(
            "Объединение сначала в группы по статусам, а потом (внутри каждой группы) в подгруппы четных и нечетных по ID")
    void tasksGroupByStatusIdEvenOdd() {
        var mapTasksList = StreamAPIExercise.getTasksGroupByStatusIdEvenOdd(taskStream);
        assertEquals(3, mapTasksList.size());
        Set<TaskStatus> expectedSetKeys = Set.of(TaskStatus.NEW, TaskStatus.ACTIVE, TaskStatus.CLOSED);
        assertThat(expectedSetKeys).isEqualTo(mapTasksList.keySet());
        assertThat(List.of(1L))
                .isEqualTo(mapTasksList.get(TaskStatus.NEW).get(false).stream()
                        .map(Task::getId)
                        .toList());
        assertThat(List.of(2L))
                .isEqualTo(mapTasksList.get(TaskStatus.NEW).get(true).stream()
                        .map(Task::getId)
                        .toList());
        assertThat(List.of(9L))
                .isEqualTo(mapTasksList.get(TaskStatus.ACTIVE).get(false).stream()
                        .map(Task::getId)
                        .toList());
        assertThat(List.of(4L, 6L))
                .isEqualTo(mapTasksList.get(TaskStatus.ACTIVE).get(true).stream()
                        .map(Task::getId)
                        .toList());
        assertThat(List.of(3L, 5L, 7L))
                .isEqualTo(mapTasksList.get(TaskStatus.CLOSED).get(false).stream()
                        .map(Task::getId)
                        .toList());
        assertThat(List.of(8L))
                .isEqualTo(mapTasksList.get(TaskStatus.CLOSED).get(true).stream()
                        .map(Task::getId)
                        .toList());
    }

    @Test
    @DisplayName("Разбивка на две группы: со статусом “Закрыто” и остальное. Группа закрытых задач")
    void TaskGroupInStatuses() {
        var closedTasksList = StreamAPIExercise.getTaskGroupByStatusAndOthers(taskStream, TaskStatus.CLOSED, true)
                .get(true);
        var testingSet = closedTasksList.stream().map(Task::getTaskStatus).collect(Collectors.toSet());
        assertEquals(Set.of(TaskStatus.CLOSED), testingSet);
    }

    @Test
    @DisplayName("Разбивка на две группы: со статусом “Закрыто” и остальное. Группа незакрытых задач")
    void TaskGroupNotInStatuses() {
        var othersTasksList = StreamAPIExercise.getTaskGroupByStatusAndOthers(taskStream, TaskStatus.CLOSED, false)
                .get(false);
        var testingTaskStatusSet =
                othersTasksList.stream().map(Task::getTaskStatus).collect(Collectors.toSet());
        assertThat(testingTaskStatusSet).isNotEmpty().doesNotContain(TaskStatus.CLOSED);
    }
}
