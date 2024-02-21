package org.example.panachedemo.controllers;

import org.example.panachedemo.exception.ProductNotFoundException;
import org.example.panachedemo.models.Product;
import org.example.panachedemo.repositories.ProductRepository;
import org.example.panachedemo.services.ProductService;
import org.example.panachedemo.services.SelfProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductControllerTest {

    @Autowired
    private ProductController productController;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

//    @Test
//    public void testGetAllProducts()
//    {
//        List<Product> productList = new ArrayList<>();
//        Product product1 = new Product();
//        product1.setId(1L);
//        product1.setPrice(1000d);
//        product1.setTitle("Prod1");
//
//        Product product2 = new Product();
//        product2.setId(2L);
//        product2.setPrice(10000d);
//        product2.setTitle("Prod2");
//
//        productList.add(product1);
//        productList.add(product2);
//        when(productService.getAllProducts()).thenReturn(productList);
//
//        List<Product> actualList = productController.getAllProducts().getBody();
//        assertEquals(productList.size(), actualList.size());
//
//        /*
//        Below snippet seems surprising because even though we changed the title in Controller it did not fail! Reason
//        is that we changed the expected value as well. You are changing that only.
//        So create a copy of the products instead of copying them as it is.
//         */
//        for(int i=0;i<actualList.size();i++)
//        {
//            assertEquals(productList.get(i).getTitle(), actualList.get(i).getTitle());
//            assertEquals(productList.get(i).getPrice(), actualList.get(i).getPrice());
//        }
//    }
//
//    @Test
//    void testProductsSameAsService() {
//        // arrange
//        List<Product> products = new ArrayList<>();
//        Product p1 = new Product(); // o
//        p1.setTitle("iPhone 15");
//        products.add(p1);
//
//        Product p2 = new Product(); // p
//        p2.setTitle("iPhone 15 Pro");
//        products.add(p2);
//
//        Product p3 = new Product(); // q
//        p3.setTitle("iPhone 15 Pro Max");
//        products.add(p3);
//
//        List<Product> prodctsToPass = new ArrayList<>();
//
//        for (Product p : products) {
//            Product p111 = new Product();
//            p111.setTitle(p.getTitle());
//            prodctsToPass.add(p111);
//        }
//
//        when(
//                productService.getAllProducts()
//        ).thenReturn(
//                prodctsToPass
//        );
//
//
//        // act
//        ResponseEntity<List<Product>> response =
//                productController.getAllProducts();
//
//        // assert
//        List<Product> productsInResponse = response.getBody();
//
//        assertEquals(products.size(), productsInResponse.size());
//
//        for (int i = 0; i < productsInResponse.size(); ++i)
//            assertEquals(
//                    products.get(i).getTitle(), // o p q
//                    productsInResponse.get(i).getTitle()
//            );
//    }

//    @Test
//    void testNonExistingProductThrowsException() throws ProductNotFoundException {
//        // arrange
//        ProductService productService1 = new SelfProductServiceImpl(productRepository, null);
//
//        when(
//                productRepository.findById(10L)
//        ).thenReturn(
//                Optional.empty()
//        );
//
//        when(
//                productService1.getSingleProduct(anyLong())
//        ).then();
//
//        // act
//        assertThrows(
//                ProductNotFoundException.class,
//                () -> productController.getSingleProduct(10L)
//        );
//
//    }

    @Test
    public void testGetSingleProductNotFound(){
        try {
            when(productService.getSingleProduct(any())).thenThrow(new ProductNotFoundException("Product doesn't exist."));
        } catch (ProductNotFoundException e) {

        }
        assertThrows(
                ProductNotFoundException.class,
                () -> productController.getSingleProduct(1L)
        );
    }

}