package com.blog.api.service;

import com.blog.api.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CategoryService {
    Category saveAndReturnCategory(String categoryName);

    Category updateCategoryByCategoryRdbmsId(Integer categoryRdbmsId, String categoryName);

    Page<Category> findAllCategory(Pageable pageable);

    Optional<Category> findCategoryByCategoryUrlName(String categoryUrlName);

    Optional<Category> findCategoryByCategoryRdbmsId(Integer categoryRdbmsId);

    void deleteCategoryByCategoryRdbmsId(Integer categoryRdbmsId);
}
