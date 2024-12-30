package ru.otus.services.impl;

import ru.otus.model.Product;
import ru.otus.services.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepositoryImpl implements ProductRepository {
    private final List<Product> products = new ArrayList<>();

    private void initProductList() {
        products.add(new Product(1L, "Молоко", 100));
        products.add(new Product(2L, "Хлеб", 80));
        products.add(new Product(3L, "Соль поваренная", 50));
        products.add(new Product(4L, "Спички", 100));
        products.add(new Product(5L, "Сахар", 120));
        products.add(new Product(6L, "Сливочное масло", 220));
        products.add(new Product(7L, "Рис", 70));
        products.add(new Product(8L, "Печенье овсяное", 40));
        products.add(new Product(9L, "Сыр плавленный", 90));
        products.add(new Product(10L, "Творог 9%", 80));
    }

    public ProductRepositoryImpl() {
        initProductList();
    }

    @Override
    public List<Product> getProducts() {
        return products;
    }

    @Override
    public Optional<Product> getProduct(long id) {
        return products.stream().filter(product -> product.getId() == id).findFirst();
    }


}
