package com.example.javawebcourse.api;

import com.example.javawebcourse.domain.Category;
import com.example.javawebcourse.domain.CosmoCat;
import com.example.javawebcourse.domain.Product;
import com.example.javawebcourse.domain.order.Order;
import com.example.javawebcourse.domain.order.OrderEntry;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
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
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @PersistenceContext
    private EntityManager entityManager;

    @DynamicPropertySource
    static void registerPostgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Test
    void testCategoryCRUD() {
        // Create
        Category category = Category.builder()
                .name("Test Category")
                .build();
        entityManager.persist(category);
        entityManager.flush();

        // Read
        Category foundCategory = entityManager.find(Category.class, category.getId());
        assertThat(foundCategory).isNotNull();
        assertThat(foundCategory.getName()).isEqualTo("Test Category");

        // Update
        foundCategory.setName("Updated Category");
        entityManager.merge(foundCategory);
        entityManager.flush();

        Category updatedCategory = entityManager.find(Category.class, foundCategory.getId());
        assertThat(updatedCategory.getName()).isEqualTo("Updated Category");

        // Delete
        entityManager.remove(updatedCategory);
        entityManager.flush();

        Category deletedCategory = entityManager.find(Category.class, updatedCategory.getId());
        assertThat(deletedCategory).isNull();
    }

    @Test
    void testCosmoCatCRUD() {
        // Create
        CosmoCat cosmoCat = CosmoCat.builder()
                .name("Test CosmoCat")
                .build();
        entityManager.persist(cosmoCat);
        entityManager.flush();

        // Read
        CosmoCat foundCosmoCat = entityManager.find(CosmoCat.class, cosmoCat.getId());
        assertThat(foundCosmoCat).isNotNull();
        assertThat(foundCosmoCat.getName()).isEqualTo("Test CosmoCat");

        // Update
        foundCosmoCat.setName("Updated CosmoCat");
        entityManager.merge(foundCosmoCat);
        entityManager.flush();

        CosmoCat updatedCosmoCat = entityManager.find(CosmoCat.class, foundCosmoCat.getId());
        assertThat(updatedCosmoCat.getName()).isEqualTo("Updated CosmoCat");

        // Delete
        entityManager.remove(updatedCosmoCat);
        entityManager.flush();

        CosmoCat deletedCosmoCat = entityManager.find(CosmoCat.class, updatedCosmoCat.getId());
        assertThat(deletedCosmoCat).isNull();
    }

    @Test
    void testProductCRUD() {
        // Create
        Product product = Product.builder()
                .name("Test Product")
                .description("Test Description")
                .price(99.99f)
                .build();
        entityManager.persist(product);
        entityManager.flush();

        // Read
        Product foundProduct = entityManager.find(Product.class, product.getId());
        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getName()).isEqualTo("Test Product");

        // Update
        foundProduct.setPrice(119.99f);
        entityManager.merge(foundProduct);
        entityManager.flush();

        Product updatedProduct = entityManager.find(Product.class, foundProduct.getId());
        assertThat(updatedProduct.getPrice()).isEqualTo(119.99f);

        // Delete
        entityManager.remove(updatedProduct);
        entityManager.flush();

        Product deletedProduct = entityManager.find(Product.class, updatedProduct.getId());
        assertThat(deletedProduct).isNull();
    }

    @Test
    void testOrderAndOrderEntryCRUD() {
        // Create Product
        Product product = Product.builder()
                .name("Order Product")
                .price(50.0f)
                .build();
        entityManager.persist(product);
        entityManager.flush();

        // Create Order Entry
        OrderEntry orderEntry = OrderEntry.builder()
                .product(product)
                .quantity(3)
                .build();

        // Create Order
        Order order = Order.builder()
                .price(150.0f)
                .build();
        order.getEntries().add(orderEntry);
        entityManager.persist(order);
        entityManager.flush();

        // Read Order
        Order foundOrder = entityManager.find(Order.class, order.getId());
        assertThat(foundOrder).isNotNull();
        assertThat(foundOrder.getEntries()).hasSize(1);
        assertThat(foundOrder.getPrice()).isEqualTo(150.0f);

        // Update Order
        foundOrder.setPrice(200.0f);
        entityManager.merge(foundOrder);
        entityManager.flush();

        Order updatedOrder = entityManager.find(Order.class, foundOrder.getId());
        assertThat(updatedOrder.getPrice()).isEqualTo(200.0f);

        // Delete Order
        entityManager.remove(updatedOrder);
        entityManager.flush();

        Order deletedOrder = entityManager.find(Order.class, updatedOrder.getId());
        assertThat(deletedOrder).isNull();
    }
}
