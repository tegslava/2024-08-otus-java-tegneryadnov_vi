package ru.otus.java_pro.spring_boot.repositories;

import java.util.List;
import java.util.Optional;
import ru.otus.java_pro.spring_boot.model.Product;

public interface ProductRepository {
    List<Product> findAll();

    Optional<Product> findById(long id);

    Product save(Product product);
}
