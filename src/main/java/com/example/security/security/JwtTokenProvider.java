package com.example.security.security;

import com.example.security.entity.Role;
import com.example.security.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

	@Value("${jwt.token.secret}")
	private String secret;
	@Value("${jwt.token.header}")
	private String header;
	@Value("${jwt.token.prefix}")
	private String prefix;
	@Value("${jwt.token.accessLife}")
	private String accessLife;
	@Value("${jwt.token.refreshLife}")
	private String refreshLife;

	private final UserService userService;

	@Autowired
	public JwtTokenProvider(UserService userService) {
		this.userService = userService;
	}

	@PostConstruct
	protected void init() {
		secret = Base64.getEncoder().encodeToString(secret.getBytes());
	}

	public String createAccessToken(String username, List<Role> roles) {
		Claims claims = Jwts.claims().setSubject(username);
		claims.put("roles", getRoleName(roles));
		Date now = new Date();
		Date valid = new Date(now + accessLife);
		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(valid)
			.signWith(SignatureAlgorithm.HS256, secret)
			.compact()
			;
	}

	public String createRefreshToken(String username, List<Role> roles) {
		Claims claims = Jwts.claims().setSubject(username);
		claims.put("roles", getRoleName(roles));
		Date now = new Date();
		Date valid = new Date(now + refreshLife);
		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(valid)
			.signWith(SignatureAlgorithm.HS256, secret)
			.compact()
			;
	}

	public List<String> createPairToken(String username, List<Role> roles) {
		List<String> result = new ArrayList<>();
		result.add(createAccessToken(username, roles));
		result.add(createRefreshToken(username, roles));
		return result;
	}

	private List<String> getRoleName(List<Role> roles) {
		List<String> result = new ArrayList<>();
		roles.forEach(role -> result.add(role.getName()));
		return result;
	}


	public String resolveToken(HttpServletRequest request) {
		return null;
	}


	public Authentication getAuthentication(String token) {
		return null;
	}
}
