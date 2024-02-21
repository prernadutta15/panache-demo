package org.example.panachedemo.dtos;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.example.panachedemo.models.BaseModel;

@Getter @Setter @Entity
public class Role extends BaseModel {
    private String name;
}
