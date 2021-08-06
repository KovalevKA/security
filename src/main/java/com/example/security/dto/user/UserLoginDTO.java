package com.example.security.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto for authorization in systen. minimum data must have to login
 */
@Data
@NoArgsConstructor
public class UserLoginDTO {

    private String username;
    private String password;

}
