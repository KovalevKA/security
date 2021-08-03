package com.example.security.service.impl;

import com.example.security.dto.UserDTO;
import com.example.security.entity.User;
import com.example.security.mapper.AbstractMapper;
import com.example.security.repository.UserRepository;
import com.example.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
