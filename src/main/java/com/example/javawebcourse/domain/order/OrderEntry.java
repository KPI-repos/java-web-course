package com.example.javawebcourse.domain.order;

import com.example.javawebcourse.domain.Product;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@Builder(toBuilder = true)
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID orderEntryId;
    @ManyToOne
    Product product;
    int quantity;
}
