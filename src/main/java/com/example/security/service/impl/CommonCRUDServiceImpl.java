package com.example.security.service.impl;

import com.example.security.dto.AbstractDTO;
import com.example.security.entity.AbstractEntity;
import com.example.security.mapper.AbstractMapper;
import com.example.security.service.CommonCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Field;
import java.util.List;

public class CommonCRUDServiceImpl<
        Entity extends AbstractEntity,
        DTO extends AbstractDTO,
        Repository extends JpaRepository<Entity, Long>,
        Mapper extends AbstractMapper<Entity, DTO>>
        implements CommonCRUDService<Entity, DTO> {

    @Autowired
    private Repository repository;
    @Autowired
    private Mapper mapper;

    @Override
    public List<Entity> getAll() {
        return repository.findAll();
    }

    @Override
    public Entity getById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public Entity create(DTO dto) {
        Entity entity = mapper.toEntity(dto);
        return repository.saveAndFlush(entity);
    }

    @Override
    public Entity update(Long id, DTO dto) {
        Entity saveEntity = repository.findById(id).get();

        for (Field field : dto.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Field saveField;
            try {
                saveField = saveEntity.getClass().getDeclaredField(field.getName());
                saveField.setAccessible(true);
                saveField.set(saveEntity, field.get(dto));
                saveField.setAccessible(false);
            } catch (Exception e) {
                throw new IllegalArgumentException("Field not found");
            }
            field.setAccessible(false);
        }
        return repository.saveAndFlush(saveEntity);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
