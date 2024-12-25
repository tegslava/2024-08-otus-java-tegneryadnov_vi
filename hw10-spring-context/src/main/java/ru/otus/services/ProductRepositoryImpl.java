package ru.otus.services;

import ru.otus.model.Product;

import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {

    @Override
    public List<Product> allProducts() {
        return List.of();
    }

    @Override
    public Product getProduct(String productId) {
        return null;
    }
}
