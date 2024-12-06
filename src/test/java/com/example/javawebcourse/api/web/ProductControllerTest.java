package com.example.javawebcourse.api.web;

import com.example.javawebcourse.domain.Category;
import com.example.javawebcourse.domain.Product;
import com.example.javawebcourse.service.ProductService;
import com.example.javawebcourse.web.ProductController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Configuration
    static class MockConfig {
        @Bean
        public ProductService productService() {
            return Mockito.mock(ProductService.class);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private Product mockProduct;
    private Category mockCategory;

    @BeforeEach
    void setUp() {
        mockCategory = Category.builder()
                .id(UUID.randomUUID())
                .name("Electronics")
                .build();

        mockProduct = Product.builder()
                .id(UUID.randomUUID())
                .category(mockCategory)
                .name("Cosmic Phone")
                .description("A futuristic phone")
                .origin("Earth")
                .price(999.99f)
                .build();
    }

    @Test
    void createProduct_ShouldReturnCreatedProduct() throws Exception {
        Mockito.when(productService.createProduct(any(Product.class))).thenReturn(mockProduct);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockProduct)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(mockProduct.getId().toString()))
                .andExpect(jsonPath("$.name").value(mockProduct.getName()))
                .andExpect(jsonPath("$.price").value(mockProduct.getPrice()));

        Mockito.verify(productService).createProduct(any(Product.class));
    }

    @Test
    void getAllProducts_ShouldReturnListOfProducts() throws Exception {
        Mockito.when(productService.getAllProducts()).thenReturn(Collections.singletonList(mockProduct));

        mockMvc.perform(get("/api/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value(mockProduct.getName()));

        Mockito.verify(productService).getAllProducts();
    }

    @Test
    void getProductById_ShouldReturnProduct_WhenFound() throws Exception {
        Mockito.when(productService.getProductById(mockProduct.getId())).thenReturn(mockProduct);

        mockMvc.perform(get("/api/products/{id}", mockProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockProduct.getId().toString()))
                .andExpect(jsonPath("$.name").value(mockProduct.getName()));

        Mockito.verify(productService).getProductById(mockProduct.getId());
    }

    @Test
    void getProductById_ShouldReturnNotFound_WhenProductDoesNotExist() throws Exception {
        UUID nonExistentId = UUID.randomUUID();
        Mockito.when(productService.getProductById(nonExistentId))
                .thenThrow(new RuntimeException("Product not found"));

        mockMvc.perform(get("/api/products/{id}", nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        Mockito.verify(productService).getProductById(nonExistentId);
    }

    @Test
    void updateProduct_ShouldReturnUpdatedProduct() throws Exception {
        Product updatedProduct = Product.builder()
                .id(mockProduct.getId())
                .category(mockCategory)
                .name("Updated Cosmic Phone")
                .description("Updated description")
                .origin("Mars")
                .price(1299.99f)
                .build();

        Mockito.when(productService.updateProduct(eq(mockProduct.getId()), any(Product.class)))
                .thenReturn(updatedProduct);

        mockMvc.perform(put("/api/products/{id}", mockProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedProduct.getId().toString()))
                .andExpect(jsonPath("$.name").value(updatedProduct.getName()));

        Mockito.verify(productService).updateProduct(eq(mockProduct.getId()), any(Product.class));
    }

    @Test
    void deleteProduct_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/products/{id}", mockProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Mockito.verify(productService).deleteProduct(mockProduct.getId());
    }

    @Test
    void deleteProduct_ShouldReturnNotFound_WhenProductDoesNotExist() throws Exception {
        UUID nonExistentId = UUID.randomUUID();
        Mockito.doThrow(new RuntimeException("Product not found")).when(productService).deleteProduct(nonExistentId);

        mockMvc.perform(delete("/api/products/{id}", nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        Mockito.verify(productService).deleteProduct(nonExistentId);
    }
}
