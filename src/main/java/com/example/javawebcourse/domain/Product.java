package com.example.javawebcourse.domain;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder(toBuilder = true)
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    @ManyToOne
    Category category;
    String name;
    String description;
    String origin;
    float price;

}