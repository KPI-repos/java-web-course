package com.example.javawebcourse.config.security.users;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;


@Schema(
    example = """
        {
            "username": "user",
            "email": "test@test.test",
            "password": "12@1!ggrRGg"
        """
)
public record PostSecurityUserDTO(
        @NotBlank String username,
        @NotBlank String email,
        @NotBlank String password
) {
}
