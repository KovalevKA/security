package com.example.security.service.impl;

import com.example.security.dto.RoleDTO;
import com.example.security.entity.Role;
import com.example.security.mapper.AbstractMapper;
import com.example.security.repository.RoleRepository;
import com.example.security.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServicseImpl
        extends CommonCRUDServiceImpl<Role, RoleDTO, RoleRepository, AbstractMapper<Role, RoleDTO>>
        implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServicseImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getByName(String roleName) {
        return roleRepository.findByName(roleName);
    }
}
