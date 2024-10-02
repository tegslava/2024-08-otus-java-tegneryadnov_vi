package ru.otus.task;

import lombok.Builder;
import lombok.Getter;

@Builder
public class Task {
    @Getter
    private long id;
    @Getter
    private String name;
    @Getter
    private TaskStatus taskStatus = TaskStatus.NEW;
}
