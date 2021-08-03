package com.example.security.mapper;

import com.example.security.dto.UserDTO;
import com.example.security.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements AbstractMapper<User, UserDTO> {

    @Override
    public UserDTO toDTO(User user) {
        return mapper.map(user, UserDTO.class);
    }

    @Override
    public User toEntity(UserDTO userDTO) {
        return mapper.map(userDTO, User.class);
    }
}
