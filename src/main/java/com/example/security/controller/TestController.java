package com.example.security.controller;

import com.example.security.dto.user.UserInfoDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class TestController {

    @GetMapping()
    public UserInfoDTO getUserInfo() {
        return null;
    }

}
