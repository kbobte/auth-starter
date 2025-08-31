package com.example.authstarter.refresh;

import java.time.Instant;

public interface RefreshTokenStore {
    void save(String username, String refreshToken, Instant expiry);
    boolean validate(String refreshToken);
    void delete(String refreshToken);
    String getUsername(String refreshToken); // returns username associated with token
}
