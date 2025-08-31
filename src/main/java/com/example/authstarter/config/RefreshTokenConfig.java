package com.example.authstarter.config;

import com.example.authstarter.refresh.RefreshTokenService;
import com.example.authstarter.refresh.RefreshTokenStore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "auth.refresh", name = "enabled", havingValue = "true")
public class RefreshTokenConfig {

    @Bean
    @ConditionalOnProperty(prefix = "auth.refresh", name = "enabled", havingValue = "true")
    public RefreshTokenService refreshTokenService(RefreshTokenStore store, AuthProperties props) {
        return new RefreshTokenService(store, props.getRefresh().getExpirySeconds());
    }
}
