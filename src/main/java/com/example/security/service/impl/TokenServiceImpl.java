package com.example.security.service.impl;

import com.example.security.dto.TokenDTO;
import com.example.security.entity.Tokens;
import com.example.security.mapper.AbstractMapper;
import com.example.security.repository.TokenRepository;
import com.example.security.service.TokenService;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl
        extends AbstractCRUDServiceImpl<Tokens, TokenDTO, TokenRepository, AbstractMapper<Tokens, TokenDTO>>
        implements TokenService {
}
