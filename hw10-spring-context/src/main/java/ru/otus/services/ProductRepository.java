package ru.otus.services;

import ru.otus.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> getProducts();
    Optional<Product> getProduct(long id);
}
