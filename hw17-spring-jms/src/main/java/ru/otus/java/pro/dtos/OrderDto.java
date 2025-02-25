package ru.otus.java.pro.dtos;

import lombok.*;

@ToString
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private String title;
    private int value;
}
