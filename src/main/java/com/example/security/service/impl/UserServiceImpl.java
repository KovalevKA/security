package com.example.security.service.impl;

import com.example.security.dto.RoleDTO;
import com.example.security.dto.TokenDTO;
import com.example.security.dto.user.UserDTO;
import com.example.security.dto.user.UserInfoDTO;
import com.example.security.dto.user.UserLoginDTO;
import com.example.security.entity.Role;
import com.example.security.entity.User;
import com.example.security.mapper.AbstractMapper;
import com.example.security.repository.UserRepository;
import com.example.security.security.JwtTokenConstruct;
import com.example.security.security.JwtUser;
import com.example.security.security.JwtUserFactory;
import com.example.security.service.RoleService;
import com.example.security.service.TokenService;
import com.example.security.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
public class UserServiceImpl
        extends CRUDServiceImpl<User, UserDTO, UserRepository, AbstractMapper<User, UserDTO>>
        implements UserService {

    private final RoleService roleService;
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final JwtTokenConstruct jwtTokenConstruct;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(EntityManager entityManager,
                           RoleService roleService,
                           TokenService tokenService,
                           UserRepository userRepository,
                           JwtTokenConstruct jwtTokenConstruct,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        super(entityManager);
        this.roleService = roleService;
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.jwtTokenConstruct = jwtTokenConstruct;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new EntityNotFoundException("User with username " + username + " not found");
        }
        JwtUser jwtUser = JwtUserFactory.create(user);
        return jwtUser;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserInfoDTO registration(UserLoginDTO dto) {
        if (findByUsername(dto.getUsername()) != null) {
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
                .map(jwtTokenConstruct.createPairToken(dto.getUsername(), Arrays.asList(role)), TokenDTO.class);
        userDTO.setToken(tokenDTO);

        return mapper.map(create(userDTO), UserInfoDTO.class);
    }

    @Override
    public Map<String, String> login(UserLoginDTO dto) {
        User user = findByUsername(dto.getUsername());
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        if (!bCryptPasswordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new CredentialsExpiredException("Credential is wrong");
        }
        user.setToken(tokenService.refreshTokens(user));
        userRepository.saveAndFlush(user);
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
        Long tokenId;
        try {
            tokenId = tokenService.getByAccessToken(header.substring(7)).getId();
        } catch (NullPointerException e) {
            throw new NullPointerException("token not found");
        }
        User user = userRepository.findByTokenId(tokenId);
        if (user == null) {
            throw new IllegalArgumentException("Access denied");
        }
        user.setToken(null);
        userRepository.saveAndFlush(user);
        tokenService.deleteById(tokenId);
    }
}
