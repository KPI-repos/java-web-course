package com.example.javawebcourse.domain.order;

import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class Order {
    UUID id;
    @Builder.Default
    List<OrderEntry> entries = java.util.Collections.emptyList();
    float price;
}