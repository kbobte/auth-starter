package com.example.authstarter.refresh;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryRefreshTokenStore implements RefreshTokenStore {

    private final Map<String, String> store = new ConcurrentHashMap<>();

    @Override
    public void save(String username, String refreshToken, Instant expiry) {
        store.put(refreshToken, username);
    }

    @Override
    public boolean validate(String refreshToken) {
        return store.containsKey(refreshToken);
    }

    @Override
    public void delete(String refreshToken) {
        store.remove(refreshToken);
    }

    @Override
    public String getUsername(String refreshToken) {
        return store.get(refreshToken);
    }
}
