package com.example.security.service;

import com.example.security.dto.TokenDTO;
import com.example.security.entity.Tokens;
import com.example.security.entity.User;

public interface TokenService extends CRUDService<Tokens, TokenDTO> {

    Tokens getByAccessToken(String accesToken);

    Tokens getByRefreshToken(String refreshToken);

    Tokens refreshTokens(User user);

}
