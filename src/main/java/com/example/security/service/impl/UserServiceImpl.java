package com.example.security.service.impl;

import com.example.security.dto.RoleDTO;
import com.example.security.dto.TokenDTO;
import com.example.security.dto.user.UserDTO;
import com.example.security.dto.user.UserInfoDTO;
import com.example.security.dto.user.UserLoginDTO;
import com.example.security.entity.Role;
import com.example.security.entity.Tokens;
import com.example.security.entity.User;
import com.example.security.mapper.AbstractMapper;
import com.example.security.repository.UserRepository;
import com.example.security.security.JwtTokenProvider;
import com.example.security.service.RoleService;
import com.example.security.service.TokenService;
import com.example.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl
        extends CommonCRUDServiceImpl<User, UserDTO, UserRepository, AbstractMapper<User, UserDTO>>
        implements UserService {

    private final RoleService roleService;
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User getByName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserInfoDTO registration(UserLoginDTO dto) {
        if (getByName(dto.getUsername()) != null) {
            throw new EntityExistsException("Can't create user. Usermane exist");
        }
        ModelMapper mapper = new ModelMapper();

        UserDTO userDTO = new UserDTO(dto.getUsername(),
                bCryptPasswordEncoder.encode(dto.getPassword()));

        Role role = roleService.getByName("ROLE_USER");
        List<RoleDTO> roleDTOs = new ArrayList<>();
        roleDTOs.add(mapper.map(role, RoleDTO.class));
        userDTO.setRoles(roleDTOs);

        TokenDTO tokenDTO = mapper
                .map(jwtTokenProvider.createPairToken(dto.getUsername(), Arrays.asList(role)),
                        TokenDTO.class);
        userDTO.setToken(tokenDTO);

        return mapper.map(create(userDTO), UserInfoDTO.class);
    }

    @Override
    public Map<String, String> login(UserLoginDTO dto) {
        User user = getByName(dto.getUsername());
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        if (!bCryptPasswordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new CredentialsExpiredException("Credential is wrong");
        }
        Map<String, String> result = new HashMap<>();
        result.put("accessToken", user.getToken().getAccessToken());
        result.put("refreshToken", user.getToken().getRefreshToken());
        return result;
    }

    @Override
    public void logout(String header) {
        if (header == null) {
            throw new IllegalArgumentException("Access denied");
        }
        if (!jwtTokenProvider.validateToken(header)) {
            throw new IllegalArgumentException("Access denied");
        }

        Tokens token = tokenService.getByAccessToken(header.substring(7));
        if (token == null) {
            throw new IllegalArgumentException("Access denied");
        }
        tokenService.deleteById(token.getId());
    }
}
