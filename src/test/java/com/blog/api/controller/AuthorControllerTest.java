package com.blog.api.controller;

import com.blog.api.entity.Author;
import com.blog.api.helper.Helper;
import com.blog.api.service.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthorControllerTest {

    @Autowired
    AuthorController authorController;

    @MockBean
    public AuthorService authorService;


    String baseUrl = "/api/blog/v1/author";

    @Autowired
    static MockMvc mockMvc;
    static ObjectMapper objectMapper;
    static ResultActions resultActions;
    static MvcResult mvcResult;
    public static Author author;

    public AuthorControllerTest(MockMvc mockMvc, ObjectMapper objectMapper, ResultActions resultActions, MvcResult mvcResult) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.resultActions = resultActions;
        this.mvcResult = mvcResult;

    }

    @Test
    public Author testAuthorRegister() throws Exception {
        this.resultActions = mockMvc.perform(post(baseUrl+"/create/")
                        .param("authorName", Helper.getUniqueString()))
                .andExpect(status().isCreated());
        this.mvcResult = this.resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Author response = this.objectMapper.readValue(contentAsString, Author.class);
        return this.author = response;
    }

    @Test
    public Author testUpdateAuthorByAuthorRdbmsId() throws Exception {
        this.resultActions = this.mockMvc.perform(put(baseUrl+"/update/by/author-id/" + author.getAuthorRdbmsId())
                .param("authorName", Helper.getUniqueString())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

        this.mvcResult = this.resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Author response = this.objectMapper.readValue(contentAsString, Author.class);
        return this.author = response;
    }

    @Test
    public void testFindAuthorByAuthorRdbmsId() throws Exception {
        mockMvc.perform(get(baseUrl+"/find/by/author-id/" + author.getAuthorRdbmsId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteAuthorByAuthorRdbmsId() throws Exception {
        this.mockMvc.perform(delete(baseUrl+"/delete/by/author-id/" + author.getAuthorRdbmsId()))
                .andExpect(status().isNoContent());
    }
}