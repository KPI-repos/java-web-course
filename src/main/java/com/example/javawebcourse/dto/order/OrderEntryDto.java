package com.example.javawebcourse.dto.order;

import java.util.UUID;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OrderEntryDto {
    @NotNull(message = "Product ID is required and cannot be null.")
    UUID productId;

    @Positive(message = "Quantity must be greater than 0.")
    @Max(value = 1000, message = "Cannot order more than 1000 items of a single product.")
    Integer quantity;
}