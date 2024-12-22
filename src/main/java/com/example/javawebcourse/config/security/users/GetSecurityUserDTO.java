package com.example.javawebcourse.config.security.users;

import java.time.ZonedDateTime;

public record GetSecurityUserDTO(
    Long id,
    String email,
    String username,
    ZonedDateTime createdTime,
    ZonedDateTime lastModifiedTime
) {
}
