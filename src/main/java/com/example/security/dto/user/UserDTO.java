package com.example.security.dto.user;

import com.example.security.dto.AbstractDTO;
import com.example.security.dto.RoleDTO;
import com.example.security.dto.TokenDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO extends AbstractDTO {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private List<RoleDTO> roles;
    private TokenDTO token;

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
