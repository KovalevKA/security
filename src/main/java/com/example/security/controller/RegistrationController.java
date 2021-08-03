package com.example.security.controller;

import com.example.security.dto.user.UserLoginDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("registration")
public class RegistrationController {

    @PostMapping("")
    public String getTest(UserLoginDTO dto) {
        return "OK";
    }

}
