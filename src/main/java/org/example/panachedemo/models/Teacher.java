package org.example.panachedemo.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Entity @ToString
public class Teacher extends BaseModel{
    private String name;
    @ManyToMany
    List<Student> studentList;
}
