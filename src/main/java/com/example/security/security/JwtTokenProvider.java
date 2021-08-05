package com.example.security.security;

import com.example.security.service.TokenService;
import com.example.security.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtTokenProvider {

    @Value("${jwt.token.secret}")
    private String secret;
    @Value("${jwt.token.header}")
    private String header;
    @Value("${jwt.token.prefix}")
    private String prefix;

    private final UserService userService;
    private final TokenService tokenService;

    @Autowired
    public JwtTokenProvider(UserService userService,
                            TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userService.loadUserByUsername(getUserName(token));
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

    public Optional<String> resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(header);
        if (bearerToken != null && bearerToken.startsWith(prefix)) {
            return Optional.of(bearerToken.substring(7, bearerToken.length()));
        }
        return Optional.empty();
    }
/**
 * TODO:edit logic to research tokens
 * */
    public Boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            if (
                    !(claimsJws.getBody().getIssuedAt().before(new Date()) &
                            claimsJws.getBody().getExpiration().after(new Date()))
            ) throw new JwtException("");
            Long userTokenId = userService.findByUsername(claimsJws.getBody().getSubject()).getToken().getId();
            if (userTokenId.equals(tokenService.getByAccessToken(token).getId()))
                return true;
            throw new JwtException("");
        } catch (JwtException | IllegalArgumentException e) {
            throw new IllegalArgumentException("Token not valid");
        }
    }



}
