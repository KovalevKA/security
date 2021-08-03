package com.example.security.security;

import com.example.security.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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
    private Long accessLife;
    @Value("${jwt.token.refreshLife}")
    private Long refreshLife;

    private final UserDetailServiceImpl userDetailService;

    @Autowired
    public JwtTokenProvider(UserDetailServiceImpl userDetailService) {
        this.userDetailService = userDetailService;
    }

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createAccessToken(String username, List<Role> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", getRoleName(roles));
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
        claims.put("roles", getRoleName(roles));
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

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailService.loadUserByUsername(getUserName(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "",
            userDetails.getAuthorities());
    }

    public String getUserName(String token) {
        return Jwts
            .parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .getBody()
            .getSubject()
            ;
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(header);
        if (bearerToken != null && bearerToken.startsWith(prefix)) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    public Boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            if (claimsJws.getBody().getExpiration().before(new Date())) {
                return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new IllegalArgumentException("Token not valid");
        }
    }

    public List<String> getRoleNames(List<Role> userRoles) {
        List<String> result = new ArrayList<>();
        userRoles.forEach(role -> result.add(role.getName()));
        return result;
    }

    private List<String> getRoleName(List<Role> roles) {
        List<String> result = new ArrayList<>();
        roles.forEach(role -> result.add(role.getName()));
        return result;
    }

}
