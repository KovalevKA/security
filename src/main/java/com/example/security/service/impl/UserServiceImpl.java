package com.example.security.service.impl;

import com.example.security.dto.user.UserDTO;
import com.example.security.dto.user.UserLoginDTO;
import com.example.security.entity.User;
import com.example.security.mapper.AbstractMapper;
import com.example.security.repository.UserRepository;
import com.example.security.security.JwtTokenProvider;
import com.example.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;

@Service
public class UserServiceImpl
        extends AbstractCRUDServiceImpl<User, UserDTO, UserRepository, AbstractMapper<User, UserDTO>>
        implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getByName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User registration(UserLoginDTO dto) {
        User user = userRepository.findByUsername(dto.getUsername());
        if (user != null)
            throw new EntityExistsException("User with username " + dto.getUsername() + " already exist");

        return null;
    }
}
