package com.example.javawebcourse.config.security.jwt;

import jakarta.servlet.http.Cookie;

import java.util.List;

public interface JwtCookieFactory {
    /**
     * Returns a list that represents a JWT cookie for the given user.
     * One cookie is httpOnly and other(s) are not.
     * Only combined they form a valid authentication token.
     *
     * @param username the user to create a cookie for
     * @return a list of cookies
     */
    List<Cookie> cookForUser(String username);
}
