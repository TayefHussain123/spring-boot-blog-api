package com.blog.api.repository;

import com.blog.api.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

    @Query(value = "SELECT * FROM categories WHERE category_name=?1" , nativeQuery = true)
    Optional<Category>  categoryNameExists(String categoryName);

    @Query(value = "SELECT * FROM categories WHERE category_url_name=?1" , nativeQuery = true)
    Optional<Category> categoryUrlNameExists(String categoryName);

    @Query(value = "SELECT * FROM categories WHERE category_url_name=?1" , nativeQuery = true)
    Optional<Category> findCategoryByCategoryUrlName(String categoryUrlName);
}
