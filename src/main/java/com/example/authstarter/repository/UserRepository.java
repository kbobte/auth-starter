package com.example.authstarter.repository;

import com.example.authstarter.model.UserPrincipal;
import java.util.Optional;

public interface UserRepository {
    Optional<UserPrincipal> findByUsername(String username);
}
