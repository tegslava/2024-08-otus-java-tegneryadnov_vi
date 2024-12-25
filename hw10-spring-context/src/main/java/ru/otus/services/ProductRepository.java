package ru.otus.services;

import ru.otus.model.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> allProducts();
    Product getProduct(String productId);
}
