package com.example.security.repository;

import com.example.security.entity.Tokens;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Tokens, Long> {

    Tokens findByAccessToken(String accessToken);

    Tokens findByRefreshToken(String refreshToken);
}
