package com.example.javawebcourse.domain.product;

import java.util.UUID;
import com.example.javawebcourse.domain.category.Category;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class Product {
    UUID id;
    Category category;
    String name;
    String description;
    String origin;
    float price;

}