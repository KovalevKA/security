package com.example.security.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

