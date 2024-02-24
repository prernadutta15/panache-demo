package org.example.panachedemo.controllers;

import org.example.panachedemo.commons.AuthenticationCommons;
import org.example.panachedemo.exception.ProductNotFoundException;
import org.example.panachedemo.models.Product;
import org.example.panachedemo.services.ProductService;
import org.example.panachedemo.vos.ProductWithSelectedFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;
    private RestTemplate restTemplate;

    private AuthenticationCommons authenticationCommons;

    @Autowired
    public ProductController(@Qualifier("fakeStoreProductService") ProductService productService,
                             RestTemplate restTemplate,
                             AuthenticationCommons authenticationCommons) {
        this.productService = productService;
        this.restTemplate = restTemplate;
        this.authenticationCommons = authenticationCommons;
    }

    @GetMapping() // localhost:8080/products
    //V1
    public ResponseEntity<List<Product>> getAllProducts(Principal principal) {
//    public ResponseEntity<List<Product>> getAllProducts(@RequestHeader("AuthenticationToken") String token) {
//        restTemplate.delete(null);

        //V0
//        UserDto userDto = authenticationCommons.validateToken(token);
//
//        if (userDto == null) {
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }

//        boolean isAdmin = false;
//
//        for (Role role: userDto.getRoles()) {
//            if (role.getName().equals("ADMIN")) {
//                isAdmin = true;
//                break;
//            }
//        }
//
//        if (!isAdmin) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        System.out.println(principal.getName());
        List<Product> products = productService.getAllProducts();
        List<Product> finalProducts = new ArrayList<>();

        for (Product p: products) { // o  p q
            p.setTitle("Hello " + p.getTitle());
            finalProducts.add(p);
        }

        ResponseEntity<List<Product>> response = new ResponseEntity<>(
                finalProducts, HttpStatus.OK
        );
        return response;
    }

    @GetMapping("/{id}")
    public Product getSingleProduct(@PathVariable("id") Long id) throws ProductNotFoundException {
        return productService.getSingleProduct(id);
    }

    @PostMapping()
    public Product addNewProduct(@RequestBody Product product) {
        return productService.addNewProduct(product);
    }

    @PatchMapping("/{id}")
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public Product updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    @PutMapping("/{id}")
    public Product replaceProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        return productService.replaceProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Long id) {

    }

    @GetMapping("/test")
    public List<Product> testProduct()
    {
        return productService.testProduct();
    }

    @GetMapping("/test2")
    public List<ProductWithSelectedFields> testProduct2()
    {
        return productService.testProductWithVos();
    }
}
