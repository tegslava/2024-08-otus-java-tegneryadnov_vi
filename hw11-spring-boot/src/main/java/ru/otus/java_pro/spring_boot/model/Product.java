package ru.otus.java_pro.spring_boot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long id;
    private String title;
    private double price;

    @Override
    public String toString() {
        return "id=" + id + ", name='" + title + '\'' + ", price=" + price;
    }
}
