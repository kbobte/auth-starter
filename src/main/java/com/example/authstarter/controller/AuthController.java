package com.example.authstarter.controller;

import com.example.authstarter.request.LoginRequest;
import com.example.authstarter.request.RefreshRequest;
import com.example.authstarter.service.JwtTokenProvider;
import com.example.authstarter.refresh.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService; // optional

    public AuthController(AuthenticationManager authManager,
                          JwtTokenProvider jwtTokenProvider,
                          @Autowired(required = false) RefreshTokenService refreshTokenService) {
        this.authManager = authManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        Authentication auth = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
        );

        String accessToken = jwtTokenProvider.createToken(username);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);

        if (refreshTokenService != null) {
            String refreshToken = refreshTokenService.createRefreshToken(username);
            tokens.put("refreshToken", refreshToken);
        }

        return tokens;
    }

    @PostMapping("/refresh")
    public Map<String, String> refresh(@RequestBody RefreshRequest payload) {

        if (refreshTokenService == null) {
            throw new UnsupportedOperationException("Refresh tokens are disabled for this product");
        }

        String refreshToken = payload.getRefresh();
        System.out.println("refresh token:" + refreshToken);

        String username = refreshTokenService.validateAndGetUsername(refreshToken);
        refreshTokenService.revoke(refreshToken);

        String newAccessToken = jwtTokenProvider.createToken(username);
        String newRefreshToken = refreshTokenService.createRefreshToken(username);

        Map<String, String> result = new HashMap<>();
        result.put("accessToken", newAccessToken);
        result.put("refreshToken", newRefreshToken);

        return result;
    }
}
