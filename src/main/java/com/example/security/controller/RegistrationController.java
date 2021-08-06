package com.example.security.controller;

import com.example.security.dto.user.UserInfoDTO;
import com.example.security.dto.user.UserLoginDTO;
import com.example.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.transaction.NotSupportedException;
import java.util.Map;

@RestController
@RequestMapping("registration")
public class RegistrationController {

    @Value("${jwt.token.header}")
    private String header;

    @Autowired
    private UserService userService;

    /**
     * Registration new user
     */
    @PostMapping
    public UserInfoDTO registration(@RequestBody UserLoginDTO dto) {
        return userService.registration(dto);
    }

    /**
     * Get access & refresh token
     */
    @PostMapping("token")
    public Map<String, String> login(@RequestBody UserLoginDTO dto) {
        return userService.login(dto);
    }


    /**
     * Logout. Delete tokens from db. User must login to get a new pair
     */
    @PostMapping("logout")
    public void logout(@RequestHeader("Authorization") String header) {
        userService.logout(header);
    }


    /**
     * Get new pair token by refresh token. right now, i don't know how it must works
     * */
    /**
     * TODO:How i can get refresh token. Header or smth else
     */
    @PostMapping("refresh-token")
    public Map<String, String> refresh() throws NotSupportedException {
        throw new NotSupportedException("refresh by refresh token not implemented right now");
    }

}
