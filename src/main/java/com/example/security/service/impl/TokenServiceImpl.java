package com.example.security.service.impl;

import com.example.security.dto.TokenDTO;
import com.example.security.entity.Tokens;
import com.example.security.entity.User;
import com.example.security.mapper.AbstractMapper;
import com.example.security.repository.TokenRepository;
import com.example.security.security.JwtTokenProvider;
import com.example.security.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl
        extends
        CRUDServiceImpl<Tokens, TokenDTO, TokenRepository, AbstractMapper<Tokens, TokenDTO>>
        implements TokenService {

    ModelMapper mapper = new ModelMapper();

    private final TokenRepository tokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Tokens getByAccessToken(String accesToken) {
        return tokenRepository.findByAccessToken(accesToken);
    }

    @Override
    public Tokens getByRefreshToken(String refreshToken) {
        return tokenRepository.findByRefreshToken(refreshToken);
    }
/**
 * TODO:can skip right now. it works if do it without respect.
 * */
    @Override
    public Tokens refreshTokens(User user) {
        TokenDTO tokenDTO = mapper
                .map(jwtTokenProvider.createPairToken(user.getUsername(), user.getRoles()), TokenDTO.class);
        return update(user.getToken().getId(), tokenDTO);
    }
}
