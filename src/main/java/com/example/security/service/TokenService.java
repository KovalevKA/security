package com.example.security.service;

import com.example.security.dto.TokenDTO;
import com.example.security.entity.Tokens;

public interface TokenService extends AbstractCRUDService<Tokens, TokenDTO> {

    Tokens getByAccessToken(String accesToken);

    Tokens getByRefreshToken(String refreshToken);

    Tokens refreshTokens(UserDTO userDTO);

}
