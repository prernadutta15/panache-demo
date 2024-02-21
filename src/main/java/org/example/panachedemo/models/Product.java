package org.example.panachedemo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Getter
@Setter
@Entity
public class Product extends BaseModel {
    private String title;
    private Double price;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn
    private Category category;

    @Column(length = 700)
    private String description;
    private String image;

}
//public class Product extends  BaseModel{
//    private String title;
//    private Double price;
////    @ManyToOne(cascade = {CascadeType.ALL})
//    @JsonBackReference
//    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinColumn
//    private Category category;
//    private String description;
//    private String image;
//}
