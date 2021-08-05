package com.example.security.service.impl;

import com.example.security.dto.RoleDTO;
import com.example.security.entity.Role;
import com.example.security.mapper.AbstractMapper;
import com.example.security.repository.RoleRepository;
import com.example.security.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class RoleServicseImpl
        extends CRUDServiceImpl<Role, RoleDTO, RoleRepository, AbstractMapper<Role, RoleDTO>>
        implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServicseImpl(EntityManager entityManager, RoleRepository roleRepository) {
        super(entityManager);
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getByName(String roleName) {
        return roleRepository.findByName(roleName);
    }
}
