package org.example.panachedemo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Student extends  BaseModel{
    private String name;
    private Double fees;
    @ManyToMany
    private List<Teacher> teacherList;
    private String description;
    private String image;
}
