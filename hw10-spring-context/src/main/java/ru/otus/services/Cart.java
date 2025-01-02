package ru.otus.services;

public interface Cart {
    void addProduct(long id);
    void removeProduct(long id);
    void showPurchase();
    void showProductsList();
}
