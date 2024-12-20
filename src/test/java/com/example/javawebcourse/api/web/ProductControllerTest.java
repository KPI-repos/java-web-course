package com.example.javawebcourse.api.web;

import com.example.javawebcourse.domain.Product;
import com.example.javawebcourse.domain.Category;
import com.example.javawebcourse.service.ProductService;
import com.example.javawebcourse.web.ProductController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void createProduct_ShouldReturnCreatedProduct() throws Exception {
        UUID categoryId = UUID.randomUUID();

        Category category = Category.builder()
                .id(categoryId)
                .name("Test Category")
                .build();

        Product product = Product.builder()
                .id(UUID.randomUUID())
                .category(category)
                .name("Test Product")
                .description("Description")
                .origin("Origin")
                .price(100.0f)
                .build();

        when(productService.createProduct(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.price").value(100.0));

        verify(productService, times(1)).createProduct(any(Product.class));
    }

    @Test
    void getAllProducts_ShouldReturnListOfProducts() throws Exception {
        UUID categoryId = UUID.randomUUID();

        Category category = Category.builder()
                .id(categoryId)
                .name("Test Category")
                .build();

        Product product = Product.builder()
                .id(UUID.randomUUID())
                .category(category)
                .name("Test Product")
                .description("Description")
                .origin("Origin")
                .price(100.0f)
                .build();

        when(productService.getAllProducts()).thenReturn(Collections.singletonList(product));

        mockMvc.perform(get("/api/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Product"))
                .andExpect(jsonPath("$[0].price").value(100.0));

        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void getProductById_ShouldReturnProduct_WhenFound() throws Exception {
        UUID id = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();

        Category category = Category.builder()
                .id(categoryId)
                .name("Test Category")
                .build();

        Product product = Product.builder()
                .id(id)
                .category(category)
                .name("Test Product")
                .description("Description")
                .origin("Origin")
                .price(100.0f)
                .build();

        when(productService.getProductById(id)).thenReturn(product);

        mockMvc.perform(get("/api/products/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.price").value(100.0));

        verify(productService, times(1)).getProductById(id);
    }

    @Test
    void updateProduct_ShouldReturnUpdatedProduct() throws Exception {
        UUID id = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();

        Category category = Category.builder()
                .id(categoryId)
                .name("Test Category")
                .build();

        Product updatedProduct = Product.builder()
                .id(id)
                .category(category)
                .name("Updated Product")
                .description("Updated Description")
                .origin("Updated Origin")
                .price(200.0f)
                .build();

        when(productService.updateProduct(eq(id), any(Product.class))).thenReturn(updatedProduct);

        mockMvc.perform(put("/api/products/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Product"))
                .andExpect(jsonPath("$.price").value(200.0));

        verify(productService, times(1)).updateProduct(eq(id), any(Product.class));
    }

    @Test
    void deleteProduct_ShouldReturnNoContent() throws Exception {
        UUID id = UUID.randomUUID();

        doNothing().when(productService).deleteProduct(id);

        mockMvc.perform(delete("/api/products/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).deleteProduct(id);
    }
}
