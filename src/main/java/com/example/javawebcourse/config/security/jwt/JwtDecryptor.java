package com.example.javawebcourse.config.security.jwt;

import io.jsonwebtoken.MalformedJwtException;

public interface JwtDecryptor {
    String extractUsername(String token) throws MalformedJwtException;
}
