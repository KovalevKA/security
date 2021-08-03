package com.example.security.service;

import com.example.security.dto.user.UserDTO;
import com.example.security.dto.user.UserInfoDTO;
import com.example.security.dto.user.UserLoginDTO;
import com.example.security.entity.User;
import java.util.Map;

public interface UserService
    extends AbstractCRUDService<User, UserDTO> {

    User getByName(String username);

    UserInfoDTO registration(UserLoginDTO dto);

    Map<String, String> login(UserLoginDTO dto);

    void logout(String header);

}
