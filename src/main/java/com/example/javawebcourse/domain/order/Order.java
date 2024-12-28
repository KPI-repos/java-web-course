package com.example.javawebcourse.domain.order;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;


@Builder(toBuilder = true)
public class Order {
    UUID id;
    List<OrderEntry> entries = new ArrayList<>();
    float price;
}