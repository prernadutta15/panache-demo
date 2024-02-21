package org.example.panachedemo.services;

import org.example.panachedemo.exception.*;
import org.example.panachedemo.models.Product;
import org.example.panachedemo.vos.ProductWithSelectedFields;

import java.util.List;

public interface ProductService {
    Product getSingleProduct(Long id) throws ProductNotFoundException;

    List<Product> getAllProducts();

    Product updateProduct(Long id, Product product);

    Product replaceProduct(Long id, Product product);

    Product addNewProduct(Product product);

    boolean deleteProduct(Long id);

    List<Product> testProduct();

    List<ProductWithSelectedFields> testProductWithVos();
}
