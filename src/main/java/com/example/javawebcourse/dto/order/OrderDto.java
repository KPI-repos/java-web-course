package com.example.javawebcourse.dto.order;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Value;


@Value
@Builder
public class OrderDto {
    @NotNull(message = "Order ID is required and cannot be null.")
    UUID id;

    @NotEmpty(message = "An order must contain at least one entry.")
    List<UUID> entryIds;

    @PositiveOrZero(message = "Order price cannot be negative.")
    float price;
}