package com.example.security.controller;

import com.example.security.entity.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class TestController {

    @PostMapping
    public String getTest(User user) {
        return "OK";
    }

}
