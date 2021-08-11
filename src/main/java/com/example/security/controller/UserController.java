package com.example.security.controller;

import com.example.security.dto.user.UserInfoDTO;
import com.example.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public UserInfoDTO getUserInfo(@RequestHeader("Authorization") String header) {
        return userService.getUserInfo(header);
    }

}
