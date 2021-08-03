package com.example.security.service.impl;

import com.example.security.dto.RoleDTO;
import com.example.security.entity.Role;
import com.example.security.mapper.AbstractMapper;
import com.example.security.repository.RoleRepository;
import com.example.security.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServicseImpl
        extends AbstractCRUDServiceImpl<Role, RoleDTO, RoleRepository, AbstractMapper<Role, RoleDTO>>
        implements RoleService {
}
