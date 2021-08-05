package com.example.security.security;

import com.example.security.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

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

    public Map<Object, Object> createPairToken(String username, List<Role> roles) {
        Map<Object, Object> result = new HashMap<>();
        result.put("accessToken", createAccessToken(username, roles));
        result.put("refreshToken", createRefreshToken(username, roles));
        return result;
    }

    public List<String> getRoleNames(List<Role> userRoles) {
        List<String> result = new ArrayList<>();
        userRoles.forEach(role -> result.add(role.getName()));
        return result;
    }

}
