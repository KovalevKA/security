package com.example.security.service.impl;

import com.example.security.dto.TokenDTO;
import com.example.security.dto.user.UserDTO;
import com.example.security.entity.Tokens;
import com.example.security.mapper.AbstractMapper;
import com.example.security.repository.TokenRepository;
import com.example.security.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl
    extends
    CommonCRUDServiceImpl<Tokens, TokenDTO, TokenRepository, AbstractMapper<Tokens, TokenDTO>>
    implements TokenService {

    private final TokenRepository tokenRepository;

    @Autowired
    public TokenServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Tokens getByAccessToken(String accesToken) {
        return tokenRepository.findByAccessToken(accesToken);
    }

    @Override
    public Tokens getByRefreshToken(String refreshToken) {
        return tokenRepository.findByRefreshToken(refreshToken);
    }

    @Override
    public Tokens refreshTokens(UserDTO userDTO) {
        return null;
    }
}
