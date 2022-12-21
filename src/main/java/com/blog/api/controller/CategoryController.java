package com.blog.api.controller;

import com.blog.api.entity.Category;
import com.blog.api.exception.BadRequestException;
import com.blog.api.exception.InternalServerErrorException;
import com.blog.api.exception.NotFoundException;
import com.blog.api.exception.UnprocessableEntityException;
import com.blog.api.helper.Helper;
import com.blog.api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/blog/v1/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    private static final int MAX_PAGE_SIZE = 20;

    @PostMapping("/create/")
    public ResponseEntity<Category> createCategory(@RequestParam String categoryName) {

        if (categoryName == null && categoryName.isBlank()) {
            throw new BadRequestException("Category name is required!");
        }

        if (categoryName.length() > 50) {
            throw new BadRequestException("Category name must not be greater than 50 characters!");
        }

        try {
            Category createCategory = categoryService.saveAndReturnCategory(categoryName);
            return new ResponseEntity<>(createCategory, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new InternalServerErrorException("Something went wrong on server!");
        }
    }


    @PutMapping("/update/by/category-id/{categoryRdbmsId}")
    public ResponseEntity<Category> updateCategoryByCategoryRdbmsId(@PathVariable("categoryRdbmsId") Integer categoryRdbmsId,
                                                                    @RequestParam("categoryName") String categoryName) {


        if (categoryRdbmsId < 1) {
            throw new BadRequestException("Invalid categoryRdbmsId!");
        }
        if (categoryName.length() > 50) {
            throw new UnprocessableEntityException("Category name must not be more than 50 characters!");
        }

        try {
            Category updateCategory = categoryService.updateCategoryByCategoryRdbmsId(categoryRdbmsId, categoryName);
            return new ResponseEntity<>(updateCategory, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            throw new InternalServerErrorException("Something went wrong on the server!");
        }
    }

    @GetMapping("/find/all/")
    public ResponseEntity<?> findAllCategories(@PageableDefault(size = MAX_PAGE_SIZE) Pageable pageable) {

        Page<Category> categories = categoryService.findAllCategory(pageable);

        if (categories.getContent().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            long totalCategory = categories.getTotalElements();
            int numberOfPageCategory = categories.getNumberOfElements();

            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Total-Count", String.valueOf(totalCategory));

            if (numberOfPageCategory < totalCategory) {
                headers.add("first", Helper.buildPageUri(PageRequest.of(0, categories.getSize())));
                headers.add("last", Helper.buildPageUri(PageRequest.of(categories.getTotalPages() - 1, categories.getSize())));

                if (categories.hasNext()) {
                    headers.add("next", Helper.buildPageUri(categories.nextPageable()));
                }

                if (categories.hasPrevious()) {
                    headers.add("prev", Helper.buildPageUri(categories.previousPageable()));
                }

                return new ResponseEntity<>(categories.getContent(), headers, HttpStatus.PARTIAL_CONTENT);
            } else {
                return new ResponseEntity<>(categories.getContent(), headers, HttpStatus.OK);
            }
        }

    }

    @GetMapping("/find/by/category-id/{categoryRdbmsId}")
    public ResponseEntity<Category> findCategoryByCategoryRdbmsId(@PathVariable("categoryRdbmsId") Integer categoryRdbmsId) {
        if (categoryRdbmsId == null) {
            throw new BadRequestException("Please provide a categoryRdbmsId with the request!");
        }

        if (categoryRdbmsId < 1) {
            throw new BadRequestException("Invalid categoryRdbmsId!");
        }

        Category theCategory = categoryService.findCategoryByCategoryRdbmsId(categoryRdbmsId)
                .orElseThrow(() -> new NotFoundException("No category found by this categoryRdbmsId!"));
        return new ResponseEntity<>(theCategory, HttpStatus.OK);
    }

    @GetMapping("/find/by/category-url-name/{categoryUrlName}")
    public ResponseEntity<Category> findCategoryByCategoryUrlName(@PathVariable("categoryUrlName") String categoryUrlName) {

        if (categoryUrlName == null || categoryUrlName.isBlank()) {
            throw new BadRequestException("Category url name is required!");
        }


        Category theCategory = categoryService.findCategoryByCategoryUrlName(categoryUrlName)
                .orElseThrow(() -> new NotFoundException("No category found by this categoryUrlName!"));
        return new ResponseEntity<>(theCategory, HttpStatus.OK);
    }


    @DeleteMapping("/delete/by/category-id/{categoryRdbmsId}")
    public ResponseEntity<Void> deleteCategoryByCategoryRdbmsId(@PathVariable("categoryRdbmsId") Integer categoryRdbmsId) {

        if (categoryRdbmsId == null) {
            throw new BadRequestException("Please provide a categoryRdbmsId with the request!");
        }

        if (categoryRdbmsId < 1) {
            throw new BadRequestException("Invalid categoryRdbmsId!");
        }

        try {
            categoryService.deleteCategoryByCategoryRdbmsId(categoryRdbmsId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            throw new InternalServerErrorException("Something went wrong on the server!");
        }
    }


}
