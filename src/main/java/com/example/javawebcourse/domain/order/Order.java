package com.example.javawebcourse.domain.order;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder(toBuilder = true)
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    @Builder.Default
            @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<OrderEntry> entries = new ArrayList<>();
    float price;
}