package ru.otus.task;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
public class Task {
    @Getter
    private long id;
    @Getter
    private String name;
    @Getter
    private TaskStatus taskStatus;
}
