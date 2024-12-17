package com.example.javawebcourse.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.example.javawebcourse.domain.Product;
import com.example.javawebcourse.web.exception.DuplicateProductNameException;
import com.example.javawebcourse.web.exception.ProductNotFoundException;

@Service
@Transactional
public class ProductService {

    private final Map<UUID, Product> productsById = new ConcurrentHashMap<>();
    private final Map<String, Product> productsByName = new ConcurrentHashMap<>();

    public Product createProduct(Product product) {
        if (productsByName.containsKey(product.getName().toLowerCase())) {
            throw new DuplicateProductNameException(product.getName());
        }

        Product newProduct = buildProduct(product);
        productsById.put(newProduct.getId(), newProduct);
        productsByName.put(newProduct.getName().toLowerCase(), newProduct);

        return newProduct;
    }

    public List<Product> getAllProducts() {
        return productsById.values().stream().collect(Collectors.toList());
    }

    public Product getProductById(UUID id) {
        return Optional.ofNullable(productsById.get(id))
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product updateProduct(UUID id, Product updatedProduct) {
        Product existingProduct = productsById.get(id);
        if (existingProduct == null) {
            throw new ProductNotFoundException(id);
        }

        Product updated = buildProduct(updatedProduct);
        productsById.put(id, updated);
        productsByName.put(updated.getName().toLowerCase(), updated);

        return updated;
    }

    public void deleteProduct(UUID id) {
        Product product = productsById.remove(id);
        if (product == null) {
            throw new ProductNotFoundException(id);
        }

        productsByName.remove(product.getName().toLowerCase());
    }

    private Product buildProduct(Product product) {
        return Product.builder()
                .id(UUID.randomUUID())
                .category(product.getCategory())
                .name(product.getName())
                .description(product.getDescription())
                .origin(product.getOrigin())
                .price(product.getPrice())
                .build();
    }


}
