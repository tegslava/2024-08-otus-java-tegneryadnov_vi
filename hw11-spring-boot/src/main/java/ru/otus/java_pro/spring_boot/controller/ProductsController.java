package ru.otus.java_pro.spring_boot.controller;

import java.util.List;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.java_pro.spring_boot.dtos.ProductDto;
import ru.otus.java_pro.spring_boot.model.Product;
import ru.otus.java_pro.spring_boot.services.ProductsService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductsController {
    private final ProductsService productsService;
    private static final Function<Product, ProductDto> ENTITY_TO_DTO =
            i -> new ProductDto(i.getId(), i.getTitle(), i.getPrice());

    @GetMapping()
    public List<ProductDto> getAllProducts() {
        return productsService.getAllProducts().stream().map(ENTITY_TO_DTO).toList();
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable(name = "id") long id) {
        return productsService.getProductById(id).map(ENTITY_TO_DTO).get();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto createNewProduct(@RequestBody ProductDto createProductDto) {
        Product newProduct = productsService.createNewProduct(createProductDto);
        return new ProductDto(newProduct.getId(), newProduct.getTitle(), newProduct.getPrice());
    }
}
