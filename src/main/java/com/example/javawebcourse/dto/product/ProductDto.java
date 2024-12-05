package com.example.javawebcourse.dto.product;

import com.example.javawebcourse.dto.validation.ValidCosmicProduct;
import com.example.javawebcourse.dto.validation.ExtendedValidation;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.util.UUID;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@GroupSequence({ ProductDto.class, ExtendedValidation.class})
public class ProductDto {
    @NotNull(message = "Product ID is required and cannot be null.")
    UUID id;

    @NotNull(message = "Category ID is required and cannot be null.")
    UUID categoryId;

    @NotBlank(message = "Product name is required and cannot be blank.")
    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters.")
    @ValidCosmicProduct(groups = ExtendedValidation.class)
    String name;

    @Size(max = 500, message = "Description cannot exceed 500 characters, including spaces.")
    String description;

    @NotBlank(message = "Origin planet is required and cannot be blank.")
    @Size(min = 2, max = 50, message = "Origin planet name must be between 2 and 50 characters.")
    String origin;

    @PositiveOrZero(message = "Price cannot be negative.")
    float price;
}