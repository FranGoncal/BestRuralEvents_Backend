package com.bestRuralEvents.AuthService.Service;


import com.bestRuralEvents.AuthService.Model.AuthUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey secretKey;
    private final long expirationMinutes;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration-minutes}") long expirationMinutes
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMinutes = expirationMinutes;
    }

    public String generateAccessToken(AuthUser user) {
        Instant now = Instant.now();
        Instant expiration = now.plusSeconds(expirationMinutes * 60);

        return Jwts.builder()
                .subject(user.getId().toString())
                .issuer("best-rural-events-auth-service")
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .claim("email", user.getEmail())
                .claim("role", user.getRole().name())
                .signWith(secretKey)
                .compact();
    }
}