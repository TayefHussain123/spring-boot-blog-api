package com.blog.api.controller;

import com.blog.api.entity.Category;
import com.blog.api.helper.Helper;
import com.blog.api.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public class CategoryControllerTest {

    @Autowired
    CategoryController categoryController;

    @MockBean
    public CategoryService categoryService;


    String baseUrl = "/api/blog/v1/category";

    @Autowired
    static MockMvc mockMvc;
    static ObjectMapper objectMapper;
    static ResultActions resultActions;
    static MvcResult mvcResult;
    public static Category category;

    public CategoryControllerTest(MockMvc mockMvc, ObjectMapper objectMapper, ResultActions resultActions, MvcResult mvcResult) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.resultActions = resultActions;
        this.mvcResult = mvcResult;

    }

    @Test
    public Category testCreateCategory() throws Exception {
        this.resultActions = mockMvc.perform(post(baseUrl+"/create/")
                        .param("categoryName", Helper.getUniqueString()))
                .andExpect(status().isCreated());
        this.mvcResult = this.resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Category response = this.objectMapper.readValue(contentAsString, Category.class);
        return this.category = response;
    }
    


    @Test
    public Category testUpdateByCategoryId() throws Exception {
        this.resultActions = this.mockMvc.perform(put(baseUrl+"/update/by/category-id/" + category.getCategoryRdbmsId())
                .param("categoryName", Helper.getUniqueString())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

        this.mvcResult = this.resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Category response = this.objectMapper.readValue(contentAsString, Category.class);
        return this.category = response;
    }
    

    @Test
    public void testFindAllCategories() throws Exception {
        mockMvc.perform(get(baseUrl+"/find/all/"))
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].categoryRdbmsId").exists());
    }

    @Test
    public void testFindByCategoryRdbmsId() throws Exception {
        mockMvc.perform(get(baseUrl+"/find/by/category-id/" + category.getCategoryRdbmsId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindByCategoryUrlName() throws Exception {
        mockMvc.perform(get(baseUrl+"/find/by/category-url-name/" + category.getCategoryUrlName()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteCategoryByCategoryRdbmsId() throws Exception {
        this.mockMvc.perform(delete(baseUrl+"/delete/by/category-id/" + category.getCategoryRdbmsId()))
                .andExpect(status().isNoContent());
    }


 
}