package com.blog.api.controller;

import com.blog.api.entity.Tag;
import com.blog.api.helper.Helper;
import com.blog.api.service.TagService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TagControllerTest {


    @Autowired
    TagController tagController;

    @MockBean
    public TagService tagService;

    @Autowired
    static MockMvc mockMvc;
    static ObjectMapper objectMapper;
    static ResultActions resultActions;
    static MvcResult mvcResult;
    public static Tag tag;



    String baseUrl = "/api/blog/v1/tag";


    public TagControllerTest(MockMvc mockMvc, ObjectMapper objectMapper, ResultActions resultActions, MvcResult mvcResult) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.resultActions = resultActions;
        this.mvcResult = mvcResult;

    }




    @Test
    public Tag testCreateTag() throws Exception {
        this.resultActions = mockMvc.perform(post(baseUrl+"/create/")
                        .param("tagName", Helper.getUniqueString()))
                .andExpect(status().isCreated());
        this.mvcResult = this.resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Tag response = this.objectMapper.readValue(contentAsString, Tag.class);
        return this.tag = response;
    }

    @Test
    public Tag testUpdateTagByTagRdbmsId() throws Exception {
        this.resultActions = this.mockMvc.perform(put(baseUrl+"/update/by/tag-id/" + tag.getTagRdbmsId())
                .param("tagName", Helper.getUniqueString())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
        this.mvcResult = this.resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Tag response = this.objectMapper.readValue(contentAsString, Tag.class);
        return this.tag = response;
    }

    @Test
    public void testFindAllTags() throws Exception {
        mockMvc.perform(get(baseUrl+"/find/all/"))
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].tagRdbmsId").exists());
    }

    @Test
    public void testFindTagByTagRdbmsId() throws Exception {
        mockMvc.perform(get(baseUrl+"/find/by/tag-id/" + tag.getTagRdbmsId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindTagByTagUrlName() throws Exception {
        mockMvc.perform(get(baseUrl+"/find/by/tag-url-name/" + tag.getTagUrlName()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteTagByTagRdbmsId() throws Exception {
        this.mockMvc.perform(delete(baseUrl+"/delete/by/tag-id/" + tag.getTagRdbmsId()))
                .andExpect(status().isNoContent());
    }
}