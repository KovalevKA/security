package com.example.security.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "roles")
@AttributeOverride(name = "id", column = @Column(name = "role_id"))
public class Role extends AbstractEntity {

    public static final String INDEX = "role";

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private String name;

}
