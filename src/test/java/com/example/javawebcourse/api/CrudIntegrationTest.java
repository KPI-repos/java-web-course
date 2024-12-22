package com.example.javawebcourse.api;

import com.example.javawebcourse.domain.Category;
import com.example.javawebcourse.domain.CosmoCat;
import com.example.javawebcourse.domain.Product;
import com.example.javawebcourse.domain.order.Order;
import com.example.javawebcourse.domain.order.OrderEntry;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback
public class CrudIntegrationTest {

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("cosmo_caTS")
            .withUsername("test123")
            .withPassword("test123");

    @PersistenceContext
    private EntityManager entityManager;

    @DynamicPropertySource
    static void registerPostgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    // ----- Category CRUD Tests -----

    @Test
    void testCreateCategory() {
        Category category = Category.builder().name("Test Category").build();
        entityManager.persist(category);
        entityManager.flush();

        assertThat(category.getId()).isNotNull();
    }

    @Test
    void testReadCategory() {
        Category category = Category.builder().name("Test Category").build();
        entityManager.persist(category);
        entityManager.flush();

        Category foundCategory = entityManager.find(Category.class, category.getId());
        assertThat(foundCategory).isNotNull();
        assertThat(foundCategory.getName()).isEqualTo("Test Category");
    }

    @Test
    void testUpdateCategory() {
        Category category = Category.builder().name("Test Category").build();
        entityManager.persist(category);
        entityManager.flush();

        category.setName("Updated Category");
        entityManager.merge(category);
        entityManager.flush();

        Category updatedCategory = entityManager.find(Category.class, category.getId());
        assertThat(updatedCategory.getName()).isEqualTo("Updated Category");
    }

    @Test
    void testDeleteCategory() {
        Category category = Category.builder().name("Test Category").build();
        entityManager.persist(category);
        entityManager.flush();

        entityManager.remove(category);
        entityManager.flush();

        Category deletedCategory = entityManager.find(Category.class, category.getId());
        assertThat(deletedCategory).isNull();
    }

    // ----- CosmoCat CRUD Tests -----

    @Test
    void testCreateCosmoCat() {
        CosmoCat cosmoCat = CosmoCat.builder().name("Test CosmoCat").build();
        entityManager.persist(cosmoCat);
        entityManager.flush();

        assertThat(cosmoCat.getId()).isNotNull();
    }

    @Test
    void testReadCosmoCat() {
        CosmoCat cosmoCat = CosmoCat.builder().name("Test CosmoCat").build();
        entityManager.persist(cosmoCat);
        entityManager.flush();

        CosmoCat foundCosmoCat = entityManager.find(CosmoCat.class, cosmoCat.getId());
        assertThat(foundCosmoCat).isNotNull();
        assertThat(foundCosmoCat.getName()).isEqualTo("Test CosmoCat");
    }

    @Test
    void testUpdateCosmoCat() {
        CosmoCat cosmoCat = CosmoCat.builder().name("Test CosmoCat").build();
        entityManager.persist(cosmoCat);
        entityManager.flush();

        cosmoCat.setName("Updated CosmoCat");
        entityManager.merge(cosmoCat);
        entityManager.flush();

        CosmoCat updatedCosmoCat = entityManager.find(CosmoCat.class, cosmoCat.getId());
        assertThat(updatedCosmoCat.getName()).isEqualTo("Updated CosmoCat");
    }

    @Test
    void testDeleteCosmoCat() {
        CosmoCat cosmoCat = CosmoCat.builder().name("Test CosmoCat").build();
        entityManager.persist(cosmoCat);
        entityManager.flush();

        entityManager.remove(cosmoCat);
        entityManager.flush();

        CosmoCat deletedCosmoCat = entityManager.find(CosmoCat.class, cosmoCat.getId());
        assertThat(deletedCosmoCat).isNull();
    }

    // ----- Product CRUD Tests -----

    @Test
    void testCreateProduct() {
        Product product = Product.builder()
                .name("Test Product")
                .description("Test Description")
                .price(99.99f)
                .build();
        entityManager.persist(product);
        entityManager.flush();

        assertThat(product.getId()).isNotNull();
    }

    @Test
    void testReadProduct() {
        Product product = Product.builder()
                .name("Test Product")
                .description("Test Description")
                .price(99.99f)
                .build();
        entityManager.persist(product);
        entityManager.flush();

        Product foundProduct = entityManager.find(Product.class, product.getId());
        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getName()).isEqualTo("Test Product");
    }

    @Test
    void testUpdateProduct() {
        Product product = Product.builder()
                .name("Test Product")
                .description("Test Description")
                .price(99.99f)
                .build();
        entityManager.persist(product);
        entityManager.flush();

        product.setPrice(119.99f);
        entityManager.merge(product);
        entityManager.flush();

        Product updatedProduct = entityManager.find(Product.class, product.getId());
        assertThat(updatedProduct.getPrice()).isEqualTo(119.99f);
    }

    @Test
    void testDeleteProduct() {
        Product product = Product.builder()
                .name("Test Product")
                .description("Test Description")
                .price(99.99f)
                .build();
        entityManager.persist(product);
        entityManager.flush();

        entityManager.remove(product);
        entityManager.flush();

        Product deletedProduct = entityManager.find(Product.class, product.getId());
        assertThat(deletedProduct).isNull();
    }

    // ----- Order CRUD Tests -----

    @Test
    void testCreateOrder() {
        Product product = Product.builder()
                .name("Order Product")
                .price(50.0f)
                .build();
        entityManager.persist(product);
        entityManager.flush();

        OrderEntry orderEntry = OrderEntry.builder()
                .product(product)
                .quantity(3)
                .build();

        Order order = Order.builder()
                .price(150.0f)
                .build();
        order.getEntries().add(orderEntry);

        entityManager.persist(order);
        entityManager.flush();

        assertThat(order.getId()).isNotNull();
    }

    @Test
    void testReadOrder() {
        Product product = Product.builder()
                .name("Order Product")
                .price(50.0f)
                .build();
        entityManager.persist(product);
        entityManager.flush();

        OrderEntry orderEntry = OrderEntry.builder()
                .product(product)
                .quantity(3)
                .build();

        Order order = Order.builder()
                .price(150.0f)
                .build();
        order.getEntries().add(orderEntry);

        entityManager.persist(order);
        entityManager.flush();

        Order foundOrder = entityManager.find(Order.class, order.getId());
        assertThat(foundOrder).isNotNull();
        assertThat(foundOrder.getEntries()).hasSize(1);
        assertThat(foundOrder.getPrice()).isEqualTo(150.0f);
    }

    @Test
    void testUpdateOrder() {
        Product product = Product.builder()
                .name("Order Product")
                .price(50.0f)
                .build();
        entityManager.persist(product);
        entityManager.flush();

        OrderEntry orderEntry = OrderEntry.builder()
                .product(product)
                .quantity(3)
                .build();

        Order order = Order.builder()
                .price(150.0f)
                .build();
        order.getEntries().add(orderEntry);

        entityManager.persist(order);
        entityManager.flush();

        order.setPrice(200.0f);
        entityManager.merge(order);
        entityManager.flush();

        Order updatedOrder = entityManager.find(Order.class, order.getId());
        assertThat(updatedOrder.getPrice()).isEqualTo(200.0f);
    }

    @Test
    void testDeleteOrder() {
        Product product = Product.builder()
                .name("Order Product")
                .price(50.0f)
                .build();
        entityManager.persist(product);
        entityManager.flush();

        OrderEntry orderEntry = OrderEntry.builder()
                .product(product)
                .quantity(3)
                .build();

        Order order = Order.builder()
                .price(150.0f)
                .build();
        order.getEntries().add(orderEntry);

        entityManager.persist(order);
        entityManager.flush();

        entityManager.remove(order);
        entityManager.flush();

        Order deletedOrder = entityManager.find(Order.class, order.getId());
        assertThat(deletedOrder).isNull();
    }
}
