package com.example.authstarter.refresh;

import java.time.Instant;
import java.util.UUID;

public class RefreshTokenService {

    private final RefreshTokenStore store;
    private final long expirySeconds;

    public RefreshTokenService(RefreshTokenStore store, long expirySeconds) {
        this.store = store;
        this.expirySeconds = expirySeconds;
    }

    public String createRefreshToken(String username) {
        String token = UUID.randomUUID().toString();
        Instant expiry = Instant.now().plusSeconds(expirySeconds);
        store.save(username, token, expiry);
        return token;
    }

    public String validateAndGetUsername(String token) {
        if (!store.validate(token)) {
            throw new RuntimeException("Invalid refresh token");
        }
        return store.getUsername(token);
    }

    public void revoke(String token) {
        store.delete(token);
    }
}
