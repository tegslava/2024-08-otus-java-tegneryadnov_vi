package ru.otus.services;

import ru.otus.model.Product;

import java.util.ArrayList;
import java.util.List;

public class CartImpl implements Cart {

    private List<Product> productList;
    private ProductRepository productRepository;

    @Override
    public void addItem(long id) {

    }

    @Override
    public void removeItem(long id) {

    }

    public CartImpl() {
    }

    public CartImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
        productList = new ArrayList<>();
    }
}
