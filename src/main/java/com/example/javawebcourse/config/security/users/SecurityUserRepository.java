package com.example.javawebcourse.config.security.users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface SecurityUserRepository extends JpaRepository<SecurityUser, Long> {
    Optional<SecurityUser> findByUsername(String username);

    void deleteByUsername(String username);
}
