import ru.otus.task.Task;
import ru.otus.task.TaskStatus;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class StreamAPIExercise {
    Stream<Task> getTaskStream() {
        return Stream.of(
                Task.builder()
                        .id(1)
                        .name("Задача 1 новая")
                        .taskStatus(TaskStatus.NEW)
                        .build(),
                Task.builder()
                        .id(2)
                        .name("Задача 2 новая")
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
                        .id(8)
                        .name("Задача 8 в работе")
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

    List<Task> getTaskListWithStatus(TaskStatus taskStatus) {
        Stream<Task> taskStream = getTaskStream();
        return taskStream.filter(t -> t.getTaskStatus() == taskStatus).toList();
    }

    int getTaskCountWithStatus(TaskStatus taskStatus) {
        Stream<Task> taskStream = getTaskStream();
        return (int) taskStream.filter(t -> t.getTaskStatus() == taskStatus).count();
    }

    boolean conditionIsMet(Predicate<?> predicate){
        Stream<Task> taskStream = getTaskStream();
        return taskStream.
    }

}
