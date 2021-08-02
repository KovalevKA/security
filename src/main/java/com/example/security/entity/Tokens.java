package com.example.security.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "tokens")
@AttributeOverride(name = "id", column = @Column(name = "token_id"))
public class Tokens extends AbstractEntity {

    @Column(name = "refresh_token")
    private String refreshToken;
    @Column(name = "access_token")
    private String accessToken;



}

