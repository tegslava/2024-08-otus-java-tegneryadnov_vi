package ru.otus.java_pro.spring_boot.services;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.java_pro.spring_boot.dtos.ProductDto;
import ru.otus.java_pro.spring_boot.model.Product;
import ru.otus.java_pro.spring_boot.repositories.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductsService {
    private final ProductRepository productRepository;

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product createNewProduct(ProductDto itemDto) {
        Product product = new Product(null, itemDto.title(), itemDto.price());
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
