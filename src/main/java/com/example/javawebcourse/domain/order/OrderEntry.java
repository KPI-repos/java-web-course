package com.example.javawebcourse.domain.order;

import com.example.javawebcourse.domain.Product;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Builder(toBuilder = true)
public class OrderEntry {
    UUID orderEntryId;
    Product product;
    int quantity;
}
