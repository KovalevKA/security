package com.example.security.security;

import com.example.security.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * it's for generate pair tokens.
 * Use SignatureAlgorithm.HS256 for encode
 */
@Component
public class JwtTokenConstruct {

    @Value("${jwt.token.secret}")
    private String secret;
    @Value("${jwt.token.accessLife}")
    private Long accessLife;
    @Value("${jwt.token.refreshLife}")
    private Long refreshLife;

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    /**
     * Generate Access token
     *
     * @param username username for whom generate token
     * @param roles    must be one role at least
     */
    public String createAccessToken(String username, List<Role> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", getRoleNames(roles));
        Date now = new Date();
        Date valid = new Date(now.getTime() + accessLife);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(valid)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact()
                ;
    }

    /**
     * Generate Refresh token
     *
     * @param username username for whom generate token
     * @param roles    must be one role at least
     */
    public String createRefreshToken(String username, List<Role> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", getRoleNames(roles));
        Date now = new Date();
        Date valid = new Date(now.getTime() + refreshLife);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(valid)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact()
                ;
    }

    /**
     * Generate Access & Refresh tokens
     *
     * @param username username for whom generate token
     * @param roles    must be one role at least
     */
    public Map<Object, Object> createPairToken(String username, List<Role> roles) {
        Map<Object, Object> result = new HashMap<>();
        result.put("accessToken", createAccessToken(username, roles));
        result.put("refreshToken", createRefreshToken(username, roles));
        return result;
    }

    /**
     * get names of roles
     *
     * @param userRoles
     * @return string names roles
     */
    private List<String> getRoleNames(List<Role> userRoles) {
        List<String> result = new ArrayList<>();
        userRoles.forEach(role -> result.add(role.getName()));
        return result;
    }

}
