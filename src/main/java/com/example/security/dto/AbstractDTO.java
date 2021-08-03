package com.example.security.dto;

import com.example.security.entity.Status;
import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.util.Date;

@Data
@MappedSuperclass
public class AbstractDTO {

    private String id;
    private Status status;
    private Date created;
    private Date updated;

}
