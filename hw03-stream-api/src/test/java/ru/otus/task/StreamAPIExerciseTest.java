package ru.otus.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Stream;

public class StreamAPIExerciseTest {
    private static Stream<Task> taskStream;

    @BeforeEach
    public void setTaskStream() {
        taskStream = Stream.of(
                Task.builder()
                        .id(1)
                        .name("Задача 2")
                        .taskStatus(TaskStatus.NEW)
                        .build(),
                Task.builder()
                        .id(2)
                        .name("Задача 2")
                        .taskStatus(TaskStatus.NEW)
                        .build(),
                Task.builder()
                        .id(3)
                        .name("Задача 3 в работе")
                        .taskStatus(TaskStatus.ACTIVE)
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
                        .id(5)
                        .name("Задача 6 закрыта")
                        .taskStatus(TaskStatus.CLOSED)
                        .build(),
                Task.builder()
                        .id(5)
                        .name("Задача 7 закрыта")
                        .taskStatus(TaskStatus.CLOSED)
                        .build()
        );
    }

    @Test
    @DisplayName("Список задач со статусом “В работе”")
    void getTaskWithStatusACTIVE() {
        List<Task> filteredTaskList = taskStream.filter(x -> x.getTaskStatus() == TaskStatus.ACTIVE).toList();
        assertThat(filteredTaskList).hasSize(2);
        assertThat(filteredTaskList.get(0).getName()).isEqualTo("Задача 3 в работе");
        assertThat(filteredTaskList.get(1).getName()).isEqualTo("Задача 4 в работе");
    }

    @Test
    @DisplayName("Количество задач со статусом “Закрыта”")
    void getTasksCountWithStatusCLOSED() {
        int cnt  = (int) taskStream.filter(x -> x.getTaskStatus() == TaskStatus.CLOSED).count();
        assertThat(cnt).isEqualTo(3);
    }
}
