package com.example.security.service;

import com.example.security.dto.RoleDTO;
import com.example.security.entity.Role;

public interface RoleService extends CommonCRUDService<Role, RoleDTO> {

    Role getByName(String roleName);
}
