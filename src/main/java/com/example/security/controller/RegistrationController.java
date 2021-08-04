package com.example.security.controller;

import com.example.security.dto.user.UserInfoDTO;
import com.example.security.dto.user.UserLoginDTO;
import com.example.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("registration")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping
    public UserInfoDTO registration(@RequestBody UserLoginDTO dto) {
        return userService.registration(dto);
    }

    @PostMapping("token")
    public Map<String, String> login(@RequestBody UserLoginDTO dto) {
        return userService.login(dto);
    }

    @PostMapping("logout")
    public void logout(@RequestHeader("Authorization") String header) {
        userService.logout(header);
    }

}
