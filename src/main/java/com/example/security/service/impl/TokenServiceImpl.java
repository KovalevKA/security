package com.example.security.service.impl;

import com.example.security.dto.TokenDTO;
import com.example.security.entity.Tokens;
import com.example.security.entity.User;
import com.example.security.mapper.AbstractMapper;
import com.example.security.repository.TokenRepository;
import com.example.security.security.JwtTokenConstruct;
import com.example.security.service.TokenService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class TokenServiceImpl
        extends
        CRUDServiceImpl<Tokens, TokenDTO, TokenRepository, AbstractMapper<Tokens, TokenDTO>>
        implements TokenService {

    ModelMapper mapper = new ModelMapper();

    private final TokenRepository tokenRepository;
    private final JwtTokenConstruct jwtTokenConstruct;

    @Autowired
    public TokenServiceImpl(EntityManager entityManager,
                            TokenRepository tokenRepository,
                            JwtTokenConstruct jwtTokenConstruct) {
        super(entityManager);
        this.tokenRepository = tokenRepository;
        this.jwtTokenConstruct = jwtTokenConstruct;
    }

    @Override
    public Tokens getByAccessToken(String accessToken) {
        return tokenRepository.findByAccessToken(accessToken);
    }

    @Override
    public Tokens getByRefreshToken(String refreshToken) {
        return tokenRepository.findByRefreshToken(refreshToken);
    }

    @Override
    public Tokens refreshTokens(User user) {
        TokenDTO tokenDTO = mapper
                .map(jwtTokenConstruct.createPairToken(user.getUsername(), user.getRoles()), TokenDTO.class);
        return create(tokenDTO);
    }
}
