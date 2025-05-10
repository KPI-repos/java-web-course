package com.example.javawebcourse.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class CosmoCatDto {
    @NotNull(message = "Cosmo Cat ID is required and cannot be null.")
    UUID id;

    @NotBlank(message = "Cosmo Cat name is required and cannot be blank.")
    @Size(min = 2, max = 100, message = "Cosmo Cat name must be between 2 and 100 characters.")
    String name;
}
