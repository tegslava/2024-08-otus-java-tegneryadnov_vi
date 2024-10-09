package ru.otus.testing.task;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Task {
    private long id;
    private String name;
    private TaskStatus taskStatus;
}
