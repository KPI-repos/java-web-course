package com.example.javawebcourse.config.security;


import com.example.javawebcourse.config.security.jwt.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@RequiredArgsConstructor
class SecurityConfig {

    private static final String[] SWAGGER_ENDPOINTS = {
        "/swagger-ui/**",
        "/v3/api-docs.yaml",
        "/v3/api-docs",
        "/v3/api-docs/**",
        "/swagger-ui.html",
    };

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .sessionManagement(sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .csrf(AbstractHttpConfigurer::disable)
            .cors(corsConfig -> corsConfig.configurationSource(a -> {
                var corsConfiguration = new CorsConfiguration();
                corsConfiguration.addAllowedOriginPattern("*");
                corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);
                corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);
                corsConfiguration.setAllowCredentials(true);
                return corsConfiguration;
            })) // TODO: configure CORS properly
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/register", "/login").permitAll()
                    .requestMatchers(SWAGGER_ENDPOINTS).permitAll()
                    .anyRequest().authenticated()
            )
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .anonymous(AbstractHttpConfigurer::disable)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }
}
