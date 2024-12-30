package ru.otus.services.impl;

import ru.otus.model.Product;
import ru.otus.services.Cart;
import ru.otus.services.IOService;
import ru.otus.services.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class CartImpl implements Cart {

    private final List<Product> purchaseList;
    private final ProductRepository repository;
    private final IOService ioService;
    private static final String MESSAGE_PURCHASE_LIST = "Товары в корзине:";

    @Override
    public void addProduct(long id) {
        repository.getProduct(id).ifPresentOrElse(
                purchaseList::add,
                () -> ioService.out("Не найден продукт id = %d".formatted(id)));
    }

    @Override
    public void removeProduct(long id) {
        purchaseList.stream().filter(product -> product.getId() == id)
                .findFirst()
                .ifPresentOrElse(
                        purchaseList::remove,
                        () -> ioService.out("Не найден продукт id = %d в корзине".formatted(id))
                );
    }

    @Override
    public void showPurchase() {
        ioService.out(MESSAGE_PURCHASE_LIST);
        purchaseList.stream().map(product -> String.format("%2d %s: %3.2f",
                product.getId(), product.getName(), product.getPrice()))
                .forEach(ioService::out);
    }

    @Override
    public void showProductsList() {
        repository.getProducts().stream().map(product -> String.format("%2d %s: %3.2f",
                product.getId(), product.getName(), product.getPrice())).forEach(ioService::out);
    }

    public CartImpl(IOService ioServiceStreams, ProductRepository productRepository) {
        this.repository = productRepository;
        this.ioService = ioServiceStreams;
        purchaseList = new ArrayList<>();
    }
}
