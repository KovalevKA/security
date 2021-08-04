package com.example.security.service;

import com.example.security.dto.AbstractDTO;
import com.example.security.entity.AbstractEntity;

import java.util.List;

public interface CRUDService<Entity extends AbstractEntity, DTO extends AbstractDTO> {

    List<Entity> getAll();

    Entity getById(Long id);

    Entity create(DTO dto);

    Entity update(Long id, DTO dto);

    void deleteById(Long id);

}
