package com.example.security.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtAuthorization extends BasicAuthenticationFilter {

    public JwtAuthorization(
        AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain chain) throws IOException, ServletException {

        String headIn = request.getHeader(SecurityConsts.HEADER);
        if (headIn == null || !headIn.startsWith(SecurityConsts.PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authentification = getAuthentification(
            headIn.replace(SecurityConsts.PREFIX, ""));

        SecurityContextHolder.getContext().setAuthentication(authentification);

        chain.doFilter(request, response);

    }

    private UsernamePasswordAuthenticationToken getAuthentification(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(SecurityConsts.SECRET)
                .parseClaimsJws(token);
            if (claimsJws.getBody().getExpiration().before(new Date())) {
                throw new JwtAuthentificationException("Token not valid");
            }
            return new UsernamePasswordAuthenticationToken(claimsJws.getBody().getSubject(), null,
                new ArrayList<>());
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthentificationException("Token not valid");
        }
    }
}
