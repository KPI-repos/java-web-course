package com.example.javawebcourse.config.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
class JwtService implements JwtDecryptor, JwtCookieFactory {

    public final Environment environment;
    private final String secret;
    private final long jwtExpiration;

    protected JwtService(Environment environment, JwtConfiguration jwtConfiguration) {
        if (jwtConfiguration.getExpiration() <= 0) {
            throw new IllegalArgumentException("JWT expiration must be greater than 0");
        }
        this.environment = environment;
        this.secret = jwtConfiguration.getSecret();
        this.jwtExpiration = jwtConfiguration.getExpiration();
    }

    @Override
    public String extractUsername(String token) throws MalformedJwtException {
        var parser = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)))
                .build();
        return parser.parseSignedClaims(token).getPayload().getSubject();
    }

    /**
     * {@inheritDoc}
     * <p>
     *
     * @param username the user to create a cookie for
     * @return
     * @implSpec Creates two cookies, one with the first half of the JWT and the other with the second half.
     * The first cookie is httpOnly, the second is not.
     * The second cookie is used to invalidate the session on logout on a client.
     */
    @Override
    public List<Cookie> cookForUser(String username) {
        var jwt = encrypt(username);

        var head = getBearerPartCookie("BearerHead", jwt.substring(0, jwt.length() / 2));
        head.setHttpOnly(true);

        var tail = getBearerPartCookie("BearerTail", jwt.substring(jwt.length() / 2));

        return List.of(head, tail);
    }

    private String encrypt(String username) {
        return Jwts
                .builder()
                .subject(username)
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .compact();
    }

    private Cookie getBearerPartCookie(String name, String value) {
        var cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge((int) jwtExpiration / 1000);

        // not applied in dev because if not ran in docker, cookies could have SameSite= Strict | Lax
        // which does not require secure attribute
        if (!environment.matchesProfiles("dev")) {
            cookie.setSecure(true);
            cookie.setAttribute("SameSite", "None");
        }

        return cookie;
    }
}
