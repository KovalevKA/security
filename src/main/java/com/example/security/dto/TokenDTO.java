package com.example.security.dto;

import lombok.Data;

@Data
public class TokenDTO extends AbstractDTO {

    private String accessToken;
    private String refreshToken;

}
