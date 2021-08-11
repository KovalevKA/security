package com.example.security.controller;

import com.example.security.dto.user.UserInfoDTO;
import com.example.security.dto.user.UserLoginDTO;
import com.example.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("registration")
public class RegistrationController {

    @Autowired
    private UserService userService;

    /**
     * Registration new user
     */
    @PostMapping
    public UserInfoDTO registration(@RequestBody UserLoginDTO dto) {
        return userService.registration(dto);
    }

}
