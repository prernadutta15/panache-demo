package org.example.panachedemo.services;

import org.example.panachedemo.dtos.FakeStoreProductDto;
import org.example.panachedemo.exception.*;
import org.example.panachedemo.models.Category;
import org.example.panachedemo.models.Product;
import org.example.panachedemo.vos.ProductWithSelectedFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpMessageConverterExtractor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * @author prerna.dutta
 */
@Service("fakeStoreProductService")
public class FakeStoreProductServiceImpl implements ProductService{

    private RestTemplate restTemplate;

    @Autowired
    public FakeStoreProductServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private Product convertFakeStoreProductToProduct(FakeStoreProductDto fakeStoreProduct) {
        Product product = new Product();
        product.setTitle(fakeStoreProduct.getTitle());
        product.setId(fakeStoreProduct.getId());
        product.setPrice(fakeStoreProduct.getPrice());
        product.setDescription(fakeStoreProduct.getDescription());
        product.setImage(fakeStoreProduct.getImage());
        product.setCategory(new Category());
        product.getCategory().setName(fakeStoreProduct.getCategory());

        return product;
    }

    private List<Product> convertFakeStoreProductListToProductList(List<FakeStoreProductDto> fakeStoreProductDtoList) {
        List<Product> productList = new ArrayList<>();
        for(FakeStoreProductDto fakeStoreProductDto: fakeStoreProductDtoList)
        {
            productList.add(convertFakeStoreProductToProduct(fakeStoreProductDto));
        }
        return productList;
    }

    @Override
    public Product getSingleProduct(Long id) throws ProductNotFoundException {
        FakeStoreProductDto productDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + id,
                FakeStoreProductDto.class
        );

        if (productDto == null) {
            throw new ProductNotFoundException(
                    "Product with id: " + id + " doesn't exist."
            );
        }

        return convertFakeStoreProductToProduct(productDto);
    }

    @Override
    public List<Product> getAllProducts()
    {
        /*
         * uncomment the code below and observe the error: cannot access class object of parameterized type
         * Even List<Object>.class won't work!
         */

//        List<FakeStoreProductDto> productDto = restTemplate.getForObject(
//                "https://fakestoreapi.com/products/",
//                List<FakeStoreProductDto>.class
//        );

        /*
         * NOTE: step over this during debug and see how it converts to a HashMap internally, that is a list of Map, so
         * how will it convert to the relevant type?
         */
//        List productDto = restTemplate.getForObject(
//                "https://fakestoreapi.com/products/",
//                List.class
//        );

//        FakeStoreProductDto[] productDto = restTemplate.getForObject(
//                "https://fakestoreapi.com/products/",
//                FakeStoreProductDto[].class
//        );
//        List<Product> productList = new ArrayList<>();
//        for(FakeStoreProductDto dto: productDto)
//            productList.add(convertFakeStoreProductToProduct(dto));
//        return productList;

          /*
           * First solution
           */
        FakeStoreProductDto[] productDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products/",
                FakeStoreProductDto[].class
        );
        List<Product> productList = new ArrayList<>();
        for(FakeStoreProductDto dto: productDto)
            productList.add(convertFakeStoreProductToProduct(dto));

         return productList;
    }

    public List<Product> getAllProductsTest() {
        List<Product> listOfProducts = new ArrayList<>();

        ResponseEntity<List<FakeStoreProductDto>> response = restTemplate.exchange(
                "https://fakestoreapi.com/products",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<FakeStoreProductDto>>(){});
        List<FakeStoreProductDto> fakeStoreProductDtoList = response.getBody();
        listOfProducts = convertFakeStoreProductListToProductList(fakeStoreProductDtoList);
        return listOfProducts;
    }

    @Override
    public Product addNewProduct(Product product) {
        HttpEntity<Product> entity = new HttpEntity<Product>(product, null);
        Product response = restTemplate.exchange(
                "https://fakestoreapi.com/products",
                HttpMethod.POST, entity,
                Product.class).getBody();
        return response;
    }

    @Override
    public boolean deleteProduct(Long id) {
        return false;
    }

    @Override
    public List<Product> testProduct() {
        return null;
    }

    @Override
    public List<ProductWithSelectedFields> testProductWithVos() {
        return null;
    }

    @Override
    public Product updateProduct(Long id, Product product) {

        /* NOTE: restTemplate.put() returns void but we need Product so we need something else
         * so go beyond tutorials and observe execute method being called from these internally, execute is more generic
         * Still people don't use execute as it is a low level impl and expects a lot of parameters. High level methods
         * help achieve abstraction so more convenient for developers.
         *
         * Another popular method is exchange, also bit higher level than execute.
         */
//        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
//        Product response = restTemplate.patchForObject(
//                "https://fakestoreapi.com/products/"+id, product,
//                Product.class, new HashMap<>());

        System.out.println(product);
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setTitle(product.getTitle());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setImage(product.getImage());

        RequestCallback requestCallback = restTemplate.httpEntityCallback(fakeStoreProductDto, FakeStoreProductDto.class);
        HttpMessageConverterExtractor<FakeStoreProductDto> responseExtractor =
                new HttpMessageConverterExtractor<>(FakeStoreProductDto.class, restTemplate.getMessageConverters());
        FakeStoreProductDto response = restTemplate.execute("https://fakestoreapi.com/products/" + id, HttpMethod.PATCH, requestCallback, responseExtractor);

        return convertFakeStoreProductToProduct(response);
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        System.out.println(product);
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setTitle(product.getTitle());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setImage(product.getImage());

        RequestCallback requestCallback = restTemplate.httpEntityCallback(fakeStoreProductDto, FakeStoreProductDto.class);
        HttpMessageConverterExtractor<FakeStoreProductDto> responseExtractor =
                new HttpMessageConverterExtractor<>(FakeStoreProductDto.class, restTemplate.getMessageConverters());
        FakeStoreProductDto response = restTemplate.execute("https://fakestoreapi.com/products/" + id, HttpMethod.PUT, requestCallback, responseExtractor);

        return convertFakeStoreProductToProduct(response);
    }
}
