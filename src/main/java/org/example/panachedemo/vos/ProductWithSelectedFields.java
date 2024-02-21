package org.example.panachedemo.vos;

import org.example.panachedemo.models.Category;

public interface ProductWithSelectedFields {
     Long getId();
     String getTitle();
     String getCategoryName();
     String getDescription();
     Double getCost();
     String getImage();
}
