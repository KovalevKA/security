package com.example.security.dto;

import com.example.security.entity.Status;
import java.util.Date;
import javax.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public class AbstractDTO {

    private String id;
    private Status status = Status.ACTIVE;
    private Date created;
    private Date updated;

}
