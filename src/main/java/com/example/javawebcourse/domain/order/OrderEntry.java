package com.example.javawebcourse.domain.order;

import com.example.javawebcourse.domain.product.Product;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class OrderEntry {

    Product product;
    int quantity;
}
