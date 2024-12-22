package com.example.javawebcourse.config.security.users;

import java.util.Optional;

public interface SecurityUserService {
    SecurityUser save(SecurityUser user);

    // The requirement for this method arises from the fact that
    // password needs to be hashed ONLY when creating a new user
    // or updating an existing user's password.
    // As there is no deterministic way to check if it is already hashed (user may
    // input a password that "looks like" a hash, but is a password
    // itself), we need to provide a method to update the user
    // without hashing the password.
    SecurityUser update(SecurityUser note);

    Optional<SecurityUser> findByUsername(String username);

    Optional<SecurityUser> findById(Long id);

    void deleteByUsername(String username);

    boolean isValidLogin(String username, String password);
}
