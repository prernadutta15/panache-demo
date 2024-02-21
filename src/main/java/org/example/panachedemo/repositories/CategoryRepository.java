package org.example.panachedemo.repositories;

import org.example.panachedemo.models.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);

    long countByIsDeletedFalse();

    long countByNameLike(String name);

    List<Category> getByNameLike(String name);

    List<Category> findTop100ByNameLikeOrderByName(String name);


}