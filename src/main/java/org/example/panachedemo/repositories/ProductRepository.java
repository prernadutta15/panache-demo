package org.example.panachedemo.repositories;

import org.example.panachedemo.vos.ProductWithSelectedFields;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Repository;
import org.example.panachedemo.models.*;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByTitleContaining(String word);

    long deleteByTitleIgnoreCase(String title);

    List<Product> findByTitleAndDescription(String title,
                                            String description);
    List<Product> findByPriceBetween(double startRange, double endRange);

    List<Product> findByCategory(Category category);

    Product findByIdAndCategoryOrderByTitle(Long id, Category category);

    List<Product> findByCategory_Id(Long id);

    List<Product> findByCategory_NameAndCategory_IsDeletedAndIsDeleted(String name, Boolean categoryDeleted, Boolean isDeleted);

    Optional<Product> findById(Long id);
    // this will return a null ifno product matches the id

    Product save(Product product);

    @Query("select p.id as id, p.title as title from Product p where p.id = :id")
    List<ProductWithSelectedFields> testQuery1(@Param("id") Long id);

    @Query(value = "select p.title as title, p.price as cost from product p where p.id = :id", nativeQuery = true)
    List<ProductWithSelectedFields> testQuery2(@Param("id") Long id);

    @Query(value = "select p.title as title, c.name as categoryName from product p " +
            "left join category c on c.id = p.category_id where p.id = :id", nativeQuery = true)
    List<ProductWithSelectedFields> testQuery3(@Param("id") Long id);
}