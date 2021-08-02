package com.example.security.entity;


import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@AttributeOverride(name = "id", column = @Column(name = "role_id"))
@Table(name = "roles")
public class Role extends AbstractEntity {

    public static final String INDEX = "role";

    @Column(name = "name")
    private String name;

}
