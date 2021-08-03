package com.example.security.mapper;

import com.example.security.dto.RoleDTO;
import com.example.security.entity.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper implements AbstractMapper<Role, RoleDTO> {

    @Override
    public RoleDTO toDTO(Role role) {
        return mapper.map(role, RoleDTO.class);
    }

    @Override
    public Role toEntity(RoleDTO roleDTO) {
        return mapper.map(roleDTO, Role.class);
    }
}
