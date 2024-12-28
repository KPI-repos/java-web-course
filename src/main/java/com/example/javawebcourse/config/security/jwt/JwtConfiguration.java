package com.example.javawebcourse.config.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties(prefix = "jwt")
@AllArgsConstructor(onConstructor_ = @ConstructorBinding)
@Getter
public class JwtConfiguration {
    @NonNull
    private final String secret;
    @NonNull
    private final Long expiration;
}
