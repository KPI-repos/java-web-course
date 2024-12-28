package com.example.javawebcourse.domain;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

@Builder(toBuilder = true)

public class Product {
    UUID id;
    Category category;
    String name;
    String description;
    String origin;
    float price;

}