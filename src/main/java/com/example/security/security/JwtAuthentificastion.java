package com.example.security.security;

import com.example.security.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtAuthentificastion extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;


    @Autowired
    public JwtAuthentificastion(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {

        try {
            User inUser = new ObjectMapper()
                .readValue(request.getInputStream(), User.class);
            return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    inUser.getUsername(),
                    inUser.getPassword(),
                    new ArrayList<>()
                )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, FilterChain chain, Authentication authResult)
        throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        Date now = new Date();
        Date valid = new Date(now.getTime() + SecurityConsts.ACCESS_TOKEN);
        String token = Jwts
            .builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(valid)
            .signWith(SignatureAlgorithm.HS256, SecurityConsts.SECRET)
            .compact();
        response.addHeader(SecurityConsts.HEADER, SecurityConsts.PREFIX + token);
    }
}
