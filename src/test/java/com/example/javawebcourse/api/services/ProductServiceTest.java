package com.example.javawebcourse.api.services;

import com.example.javawebcourse.domain.Category;
import com.example.javawebcourse.domain.Product;
import com.example.javawebcourse.service.ProductService;
import com.example.javawebcourse.web.exception.DuplicateProductNameException;
import com.example.javawebcourse.web.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    private ProductService productService;

    private static final UUID FIXED_UUID_1 = UUID.fromString("c7ae9b0c-8d6a-4117-bbd1-8f1d76891905");
    private static final UUID FIXED_UUID_2 = UUID.fromString("2c06ada5-3aeb-487b-8521-72fc9baf1a7a");

    private static final Category MOCK_CATEGORY = Category.builder()
            .id(UUID.randomUUID())
            .name("Electronics")
            .build();

    private static final Category MOCK_CATEGORY_2 = Category.builder()
            .id(UUID.randomUUID())
            .name("Fashion")
            .build();

    private static final Product MOCK_PRODUCT = Product.builder()
            .id(FIXED_UUID_1)
            .category(MOCK_CATEGORY)
            .name("Test Product")
            .description("Test Description")
            .origin("Earth")
            .price(100.0f)
            .build();

    private static final Product MOCK_PRODUCT_2 = Product.builder()
            .id(FIXED_UUID_2)
            .category(MOCK_CATEGORY_2)
            .name("Test Product 2")
            .description("Another Test Description")
            .origin("Mars")
            .price(200.0f)
            .build();


    @BeforeEach
    void setUp() {
        productService = new ProductService();
        productService.createProduct(MOCK_PRODUCT);
    }


    @Test
    void createProduct_ShouldAddProduct() {
        Product createdProduct = productService.createProduct(MOCK_PRODUCT_2);

        assertNotNull(createdProduct);
        assertEquals(MOCK_PRODUCT_2.getName(), createdProduct.getName());
        assertEquals(2, productService.getAllProducts().size());
    }

    @Test
    void createProduct_ShouldThrowException_WhenDuplicateName() {
        assertThrows(DuplicateProductNameException.class, () -> productService.createProduct(MOCK_PRODUCT));
    }

    @Test
    void getAllProducts_ShouldReturnListOfProducts() {
        List<Product> products = productService.getAllProducts();

        assertEquals(1, products.size());
        assertEquals(MOCK_PRODUCT.getName(), products.get(0).getName());
    }

    @Test
    void getProductById_ShouldReturnProduct_WhenFound() {
        Product product = productService.getProductById(FIXED_UUID_1);

        assertNotNull(product);
        assertEquals(MOCK_PRODUCT.getName(), product.getName());
    }

    @Test
    void getProductById_ShouldThrowException_WhenNotFound() {
        UUID nonExistentId = UUID.randomUUID();

        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(nonExistentId));
    }

    @Test
    void updateProduct_ShouldUpdateProduct_WhenFound() {
        Product updatedProduct = Product.builder()
                .id(FIXED_UUID_1)
                .category(MOCK_CATEGORY)
                .name("Updated Test Product")
                .description("Updated Description")
                .origin("Venus")
                .price(150.0f)
                .build();

        Product result = productService.updateProduct(FIXED_UUID_1, updatedProduct);

        assertNotNull(result);
        assertEquals("Updated Test Product", result.getName());
        assertEquals("Updated Description", result.getDescription());
        assertEquals(1, productService.getAllProducts().size());
    }

    @Test
    void updateProduct_ShouldThrowException_WhenNotFound() {
        UUID nonExistentId = UUID.randomUUID();
        Product updatedProduct = Product.builder()
                .id(nonExistentId)
                .category(MOCK_CATEGORY_2)
                .name("Non-existent Product")
                .description("This product does not exist")
                .origin("Jupiter")
                .price(299.99f)
                .build();

        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(nonExistentId, updatedProduct));
    }

    @Test
    void deleteProduct_ShouldRemoveProduct_WhenFound() {
        productService.deleteProduct(FIXED_UUID_1);

        assertEquals(0, productService.getAllProducts().size());
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(FIXED_UUID_1));
    }

    @Test
    void deleteProduct_ShouldThrowException_WhenNotFound() {
        UUID nonExistentId = UUID.randomUUID();

        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(nonExistentId));
    }
}
