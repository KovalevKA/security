package com.example.security.service;

import com.example.security.dto.UserDTO;
import com.example.security.entity.User;

public interface UserService
        extends AbstractCRUDService<User, UserDTO> {

    User getByName(String username);

}
