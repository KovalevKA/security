package com.example.security.dto.user;

import com.example.security.dto.AbstractDTO;
import com.example.security.dto.RoleDTO;
import com.example.security.dto.TokenDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UserInfoDTO extends AbstractDTO {

    private String username;
    private String firstName;
    private String lastName;
    private List<RoleDTO> roles = new ArrayList<>();
    private TokenDTO token;

}
