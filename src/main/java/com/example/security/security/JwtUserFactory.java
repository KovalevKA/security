package com.example.security.security;

import com.example.security.entity.Role;
import com.example.security.entity.Status;
import com.example.security.entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
            user.getUsername(),
            user.getPassword(),
            user.equals(Status.ACTIVE),
            user.getUpdated(),
            mapToGrantedAuthority(new ArrayList<>(user.getRoles()))
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthority(List<Role> roles) {
        return roles.stream()
            .map(role -> new SimpleGrantedAuthority(role.getName()))
            .collect(Collectors.toList())
            ;
    }
}
