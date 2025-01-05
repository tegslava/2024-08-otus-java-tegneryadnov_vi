package ru.otus.java_pro.spring_boot.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import ru.otus.java_pro.spring_boot.model.Product;

@Repository
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
    public List<Product> findAll() {
        return products;
    }

    @Override
    public Optional<Product> findById(long id) {
        return products.stream().filter(product -> product.getId() == id).findFirst();
    }

    @Override
    public Product save(Product product) {
        long nextId = products.size() + 1L;
        product.setId(nextId);
        products.add(product);
        return product;
    }
}
