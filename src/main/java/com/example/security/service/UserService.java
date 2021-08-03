package com.example.security.service;

import com.example.security.dto.user.UserDTO;
import com.example.security.dto.user.UserLoginDTO;
import com.example.security.entity.User;

public interface UserService
        extends AbstractCRUDService<User, UserDTO> {

    User getByName(String username);

    User registration(UserLoginDTO dto);

}
