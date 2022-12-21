package com.blog.api.controller;

import com.blog.api.dto.PostDto;
import com.blog.api.entity.Author;
import com.blog.api.entity.Category;
import com.blog.api.entity.Post;
import com.blog.api.entity.Tag;
import com.blog.api.helper.EntityState;
import com.blog.api.helper.Helper;
import com.blog.api.repository.CategoryRepository;
import com.blog.api.repository.PostRepository;
import com.blog.api.repository.TagRepository;
import com.blog.api.service.CategoryService;
import com.blog.api.service.PostService;
import com.blog.api.service.PostTagService;
import com.blog.api.service.TagService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostControllerTest {

    @Mock
    PostDto postDto;
    @Autowired
    AuthorController authorController;
    @Autowired
    CategoryController categoryController;
    @Autowired
    TagController tagController;
    @Autowired
    PostController postController;

    @Mock
    private CategoryService categoryService;
    @Mock
    private TagService tagService;
    @Mock
    private PostService postService;

    @Mock
    private PostTagService postTagService;

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private PostRepository postRepository;

    @Autowired
    static ObjectMapper objectMapper;
    static MockMvc mockMvc;
    static ResultActions resultActions;
    static MvcResult mvcResult;
    static Author author;
    static Category category;
    static Tag tags;
    public static Post post;

    String baseUrl = "/api/blog/v1/post";

    public  PostControllerTest(){

    }
    public PostControllerTest(MockMvc mockMvc, ObjectMapper objectMapper, ResultActions resultActions, MvcResult mvcResult, Author author, Category category, Tag tags) {
        this.mockMvc=mockMvc;
        this.objectMapper=objectMapper;
        this.resultActions=resultActions;
        this.mvcResult=mvcResult;
        this.author=author;
        this.category=category;
        this.tags= tags;
        postDto=new PostDto();
    }




    public  Post testCreatePost() throws Exception {

        List<Integer> postTag= new ArrayList<>();
        postTag.add(tags.getTagRdbmsId());

        postDto= new PostDto(Helper.getUniqueString(),"Post description",this.author.getAuthorRdbmsId(),this.category.getCategoryRdbmsId(), EntityState.ACTIVE.toString(),postTag);
        String jsonString= this.objectMapper.writeValueAsString(postDto);


        this.resultActions = this.mockMvc.perform(post(baseUrl+"/create/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonString)).andExpect(( status().isCreated()));

        MvcResult mvcResult=resultActions.andReturn();
        String contentAsString=mvcResult.getResponse().getContentAsString();
        Post response=this.objectMapper.readValue(contentAsString,Post.class);

        return this.post=response;

    }

    public  Post testUpdatePostByPostRdbmsId() throws Exception {

        List<Integer> postTag= new ArrayList<>();
        postTag.add(tags.getTagRdbmsId());

        postDto= new PostDto(Helper.getUniqueString(),"Post description",this.author.getAuthorRdbmsId(),this.category.getCategoryRdbmsId(), EntityState.ACTIVE.toString(),postTag);
        String jsonString= this.objectMapper.writeValueAsString(postDto);


        this.resultActions = this.mockMvc.perform(put(baseUrl+"/update/by/post-id/"+post.getPostRdbmsId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonString)).andExpect(( status().isAccepted()));

        MvcResult mvcResult=resultActions.andReturn();
        String contentAsString=mvcResult.getResponse().getContentAsString();
        Post response=this.objectMapper.readValue(contentAsString,Post.class);

        return this.post=response;

    }


    public void testPostDeleteByPostRdbmsId() throws Exception {
        this.mockMvc.perform(delete(baseUrl+"/delete/by/post-id/"+post.getPostRdbmsId())
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

    }
}