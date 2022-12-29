package com.blog.api.service;

import com.blog.api.entity.Category;
import com.blog.api.exception.InternalServerErrorException;
import com.blog.api.exception.NotFoundException;
import com.blog.api.exception.UnprocessableEntityException;
import com.blog.api.helper.EntityState;
import com.blog.api.helper.Helper;
import com.blog.api.repository.CategoryRepository;
import com.blog.api.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Optional;


@Service
@Transactional
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public Category saveAndReturnCategory(String categoryName) {

        if (categoryRepository.categoryNameExists(categoryName).isPresent()) {
            throw new UnprocessableEntityException("Category name already exists!");
        }

        Category theCategory = new Category();
        theCategory.setCategoryName(categoryName);
        theCategory.setCategoryUrlName(categoryRepository.categoryUrlNameExists(Helper.toSlug(categoryName)).isPresent()  ? Helper.toSlug(categoryName+ Helper.getUniqueString()) : Helper.toSlug(categoryName));
        theCategory.setCategoryState(EntityState.ACTIVE.toString());
        theCategory.setCreatedAt(Helper.getCurrentTimestamp());
        theCategory.setUpdatedAt(Helper.getCurrentTimestamp());

        try {
            return categoryRepository.save(theCategory);
        } catch (Exception e) {
            throw new InternalServerErrorException("Something went wrong on the server!");
        }



    }

    @Override
    public Category updateCategoryByCategoryRdbmsId(Integer categoryRdbmsId, String categoryName) {

        Category theCategory = categoryRepository.findById(categoryRdbmsId)
                .orElseThrow(()-> new NotFoundException("No category found by this categoryRdbmsId!"));

        if (theCategory.getCategoryName().equals(categoryName)){
            throw new UnprocessableEntityException("Category name already exists!");
        }
        if (categoryName != null && !categoryName.isBlank()) {
            theCategory.setCategoryName(categoryName);
            theCategory.setCategoryUrlName(categoryRepository.categoryUrlNameExists(Helper.toSlug(categoryName)).isPresent()  ? Helper.toSlug(categoryName+ Helper.getUniqueString()) : Helper.toSlug(categoryName));
        }

        try {
            return categoryRepository.save(theCategory);
        } catch (Exception e) {
            throw new InternalServerErrorException("Something went wrong on the server!");
        }

    }

    @Override
    public Page<Category> findAllCategory(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Optional<Category> findCategoryByCategoryRdbmsId(Integer categoryRdbmsId) {
        return categoryRepository.findById(categoryRdbmsId);

    }

    @Override
    public Optional<Category> findCategoryByCategoryUrlName(String categoryUrlName) {
        return categoryRepository.findCategoryByCategoryUrlName(categoryUrlName);
    }

    @Override
    public void deleteCategoryByCategoryRdbmsId(Integer categoryRdbmsId) {
        Category theCategory = categoryRepository.findById(categoryRdbmsId)
                .orElseThrow(() -> new NotFoundException("No category found by this categoryRdbmsId!"));
        try {
            categoryRepository.deleteById(theCategory.getCategoryRdbmsId());
        } catch (Exception e) {
            throw new InternalServerErrorException("Something went wrong on the server!");
        }
    }


}
