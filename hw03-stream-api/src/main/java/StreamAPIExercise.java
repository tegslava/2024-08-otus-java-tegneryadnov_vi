import ru.otus.task.Task;
import ru.otus.task.TaskStatus;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamAPIExercise {
    public static Stream<Task> getTaskStream() {
        return Stream.of(
                Task.builder()
                        .id(1)
                        .name("Задача 1 открыта")
                        .taskStatus(TaskStatus.NEW)
                        .build(),
                Task.builder()
                        .id(2)
                        .name("Задача 2 откыта")
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
                        .id(6)
                        .name("Задача 6 закрыта")
                        .taskStatus(TaskStatus.CLOSED)
                        .build(),
                Task.builder()
                        .id(7)
                        .name("Задача 7 закрыта")
                        .taskStatus(TaskStatus.CLOSED)
                        .build()
        );
    }

    List<Task> getTasksListWithStatus(TaskStatus taskStatus) {
        Stream<Task> taskStream = getTaskStream();
        return taskStream.filter(t -> t.getTaskStatus() == taskStatus).toList();
    }

    int getTaskCountWithStatus(TaskStatus taskStatus) {
        Stream<Task> taskStream = getTaskStream();
        return (int) taskStream.filter(t -> t.getTaskStatus() == taskStatus).count();
    }

    //boolean conditionIsMet(Predicate<?> predicate){
    boolean conditionIsMet(Long existsId, Long notExistsId) {
        Stream<Task> taskStream = getTaskStream();
        /*        var m = taskStream*//*.filter(task -> task.getId() > 0)*//*
                .collect(Collectors.toMap(Task::getId, task -> task));
        return m.containsKey(existsId) && !m.containsKey(notExistsId);*/
        var s = taskStream/*.filter(task -> task.getId() > 0)*/
                .map(Task::getId).collect(Collectors.toSet());
        return s.contains(existsId) && !s.contains(notExistsId);
    }

    public static void main(String[] args) {
        Stream<Task> taskStream = getTaskStream();
        Predicate<Task> startPredicate = task -> task.getId() == 2;
        Predicate<Task> endPredicate = task -> task.getId() == 99;
        /*boolean condition1 = (taskStream.anyMatch(startPredicate));
        Stream<Task> taskStream2 = getTaskStream();
        boolean condition2 = (taskStream2.anyMatch(endPredicate));*/
        //System.out.println(taskStream.filter(startPredicate).anyMatch(endPredicate));

/*        long c1 =  taskStream.filter(startPredicate).count();
        long c2 = taskStream.filter(endPredicate).count();
        */
        //var s = taskStream.filter(task->task.getId()==99?Stream.empty():task).forEach(System.out::println);

        /*        var s = taskStream*//*.filter(task -> task.getId() > 0)*//*
                .map(Task::getId).collect(Collectors.toSet());
        System.out.println(s.contains(2L) && !s.contains(99L));*/
        ;
        //getTasksListSortedByStatus();
        //getTasksGroupByStatus();
        //getTaskGroup();
        getTasksListSortedByStatus();

    }

    static /*List<Task>*/ void getTasksListSortedByStatus() {
        Stream<Task> taskStream = getTaskStream();
        Comparator<Task> taskComparator = Comparator.comparingInt(t -> t.getTaskStatus().ordinal());
        //Comparator<Task> taskComparator = Comparator.comparingInt(Task::getTaskStatus().ordinal());
        //gget(Test.class).priority().ordinal()).reversed();
        //taskStream.sorted(taskComparator).toList().forEach(System.out::println);
        System.out.println(taskStream.sorted(taskComparator).map(Task::getTaskStatus).toList());
        //Comparator<Method> comparator = Comparator.comparingInt((Method a) -> a.getAnnotation(Test.class).priority().ordinal()).reversed();
    }

    static Map<TaskStatus, Map<Boolean, List<Task>>> getTasksGroupByStatus() {
        Stream<Task> taskStream = getTaskStream();
        //Map<TaskStatus, List<Task>> mapTasksList =
        /*var mapTasksList =
                taskStream.collect(
                        Collectors.groupingBy(Task::getTaskStatus*//*,
                        Collectors.groupingBy(Task::getId)*//*));
         */
        return taskStream.collect(
                Collectors.groupingBy(Task::getTaskStatus,
                        Collectors.groupingBy(k -> (k.getId() % 2L) == 0)));
        //Collectors.groupingBy((Task::getId) % 2L) == 0)));


        //System.out.println();
        //var taskComparator = Comparator.comparingInt((Task t)->t.getTaskStatus().ordinal());
        //mapTasksList.entrySet().stream().map(e->"Group "+e.getKey()+" Task: "+e.getValue()).toList().forEach(System.out::println);
        //System.out.println(tasksStatusGroups.toString());

    }

    static List<Task> getTaskGroupByStatus(TaskStatus st) {
        Stream<Task> taskStream = getTaskStream();
        return taskStream
                .collect(Collectors.partitioningBy(task -> task.getTaskStatus() == st)).get(true);
    }

    static void getTaskGroup() {
/*        Stream<Task> taskStream = getTaskStream();
        List<Task> activeTasks = taskStream
                .collect(Collectors.partitioningBy(task -> task.getTaskStatus() == TaskStatus.ACTIVE)).get(true);
        List<Task> closedTasks = taskStream
                .collect(Collectors.partitioningBy(task -> task.getTaskStatus() == TaskStatus.CLOSED)).get(true);
        List<Task> newTasks = taskStream
                .collect(Collectors.partitioningBy(task -> task.getTaskStatus() == TaskStatus.NEW)).get(true);
        List<Task> resolvedTasks = taskStream
                .collect(Collectors.partitioningBy(task -> task.getTaskStatus() == TaskStatus.RESOLVED)).get(true);

        List<Task> ts = taskStream
                .collect(Collectors.partitioningBy(task -> task.getTaskStatus() == < T extends TaskStatus > s)).
        get(true);*/

        System.out.println(getTaskGroupByStatus(TaskStatus.CLOSED));
    }
}

