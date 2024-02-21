package org.example.panachedemo.services;

import org.example.panachedemo.models.*;
import org.example.panachedemo.repositories.CategoryRepository;
import org.example.panachedemo.repositories.ProductRepository;
import org.example.panachedemo.exception.*;
import org.example.panachedemo.vos.ProductWithSelectedFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Primary
@Service("selfProductService")
public class SelfProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public SelfProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getSingleProduct(Long id) throws ProductNotFoundException{

        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isEmpty()) {
            throw new ProductNotFoundException("Product with id: " + id + " doesn't exist.");
        }

        Product product = productOptional.get();
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        return null;
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isEmpty())throw new RuntimeException();
        Product savedProduct = productOptional.get();

        if(product.getTitle()!=null)
            savedProduct.setTitle(product.getTitle());
        if(product.getDescription()!=null)
            savedProduct.setDescription(product.getDescription());
        if(product.getPrice()!=null)
            savedProduct.setPrice(product.getPrice());

        return productRepository.save(savedProduct);
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        return null;
    }

    @Override
    public Product addNewProduct(Product product) {
        Optional<Category> categoryOptional = categoryRepository.findByName(product.getCategory().getName());

        //without cascade type as all
//        if (categoryOptional.isEmpty()) {
//            Category savedCategory = categoryRepository.save(product.getCategory());
//            product.setCategory(savedCategory);
//        } else {
//            product.setCategory(categoryOptional.get());
//        }

        //using cascade, if I add a new product with new category, automatically creates a new category and saves it
        if (categoryOptional.isEmpty()) {

        } else {
            product.setCategory(categoryOptional.get());
        }
        return productRepository.save(product);
    }

    @Override
    public boolean deleteProduct(Long id) {
        return false;
    }

    @Override
    public List<Product> testProduct()
    {
        List<Product> productList = new ArrayList<>();
        long ans = categoryRepository.countByIsDeletedFalse();
        System.out.println("Number of active categories: "+ans);

        ans = categoryRepository.countByNameLike("%s%");
        System.out.println("Number of category names containing s: "+ans);

        List<Category> categories = categoryRepository.findTop100ByNameLikeOrderByName("%s%");
        for(Category c: categories)
            System.out.println(c);
        System.out.println();

        Optional<Category> category = categoryRepository.findByName("Books");
        System.out.println(category.get());
        if(category.isEmpty())
        {

        }

        UUID d = UUID.randomUUID();
        System.out.println(d);
        productList = productRepository.findByCategory(category.get());

        productList = productRepository.findByCategory_NameAndCategory_IsDeletedAndIsDeleted("Books", false,true);
        return productList;
    }

    @Override
    public List<ProductWithSelectedFields> testProductWithVos()
    {
        List<ProductWithSelectedFields> productList = new ArrayList<>();
        productList = productRepository.testQuery1(1L);
        System.out.println("Query 1 result:");
        for(ProductWithSelectedFields product: productList)
            System.out.println(product.getId()+" "+product.getTitle());
        productList = productRepository.testQuery2(1L);
        System.out.println("Query 2 result:");
        for(ProductWithSelectedFields product: productList)
            System.out.println(product.getTitle()+" "+product.getCost());
        productList = productRepository.testQuery3(1L);
        System.out.println("Query 3 result:");
        for(ProductWithSelectedFields product: productList)
            System.out.println(product.getTitle()+" "+product.getCategoryName());
        return productList;
    }
}