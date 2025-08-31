package com.example.authstarter.refresh;

public interface RefreshTokenServiceInterface {
    String createRefreshToken(String username);
    String validateAndGetUsername(String refreshToken);
}
