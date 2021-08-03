package com.example.security.mapper;

import com.example.security.dto.TokenDTO;
import com.example.security.entity.Tokens;
import org.springframework.stereotype.Component;

@Component
public class TokenMapper implements AbstractMapper<Tokens, TokenDTO> {
    @Override
    public TokenDTO toDTO(Tokens tokens) {
        return mapper.map(tokens, TokenDTO.class);
    }

    @Override
    public Tokens toEntity(TokenDTO tokenDTO) {
        return mapper.map(tokenDTO, Tokens.class);
    }
}
