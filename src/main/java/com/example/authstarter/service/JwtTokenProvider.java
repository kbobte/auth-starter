package com.example.authstarter.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

public class JwtTokenProvider {
    private final Key key;
    private final long expirationMs;

    public JwtTokenProvider(String secret, long expirationSeconds) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationMs = expirationSeconds * 1000;
    }

    public String generateToken(String username) {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
            .parseClaimsJws(token).getBody().getSubject();
    }
}
