package com.example.security.controller;

import com.example.security.dto.user.UserInfoDTO;
import com.example.security.dto.user.UserLoginDTO;
import com.example.security.service.UserService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("registration")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping
    public UserInfoDTO registration(@RequestBody UserLoginDTO dto) {
        return userService.registration(dto);
    }

    @PostMapping("login")
    public Map<String, String> login(@RequestBody UserLoginDTO dto) {
        return userService.login(dto);
    }

    @PostMapping("logout")
    public void logout(@RequestHeader("Authorization") String header) {
        userService.logout(header);
    }

}
