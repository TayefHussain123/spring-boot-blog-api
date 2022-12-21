package com.blog.api;

import com.blog.api.controller.*;
import com.blog.api.entity.Author;
import com.blog.api.entity.Category;
import com.blog.api.entity.Post;
import com.blog.api.entity.Tag;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BlogApiApplicationTests {

	@Autowired
	MockMvc mockMvc;


	@Mock
	AuthorControllerTest authorControllerTest;

	@Mock
	CategoryControllerTest categoryControllerTest;

	@Mock
	TagControllerTest tagControllerTest;

	@Mock
	PostControllerTest postControllerTest;


	@Autowired
	ObjectMapper objectMapper;
	MvcResult mvcResult;
	ResultActions resultActions;

	public static Post post;
	public static Author author;
	public static Category category;
	public static Tag tag;


	@BeforeEach()
	void init() {
		postControllerTest=new PostControllerTest(mockMvc,objectMapper,resultActions,mvcResult,author,category, tag);

		authorControllerTest = new AuthorControllerTest(mockMvc, objectMapper, resultActions, mvcResult);
		categoryControllerTest = new CategoryControllerTest(mockMvc, objectMapper, resultActions, mvcResult);
		tagControllerTest = new TagControllerTest(mockMvc, objectMapper, resultActions, mvcResult);

	}



	@Test
	@Order(1)
	@DisplayName("01 - Should pass if the testAuthorRegister return valid response.")
	public void testAuthorRegister() throws Exception {
		authorControllerTest.testAuthorRegister();
		author = authorControllerTest.author;
	}


	@Test
	@Order(2)
	@DisplayName("02 - Should pass if the testCreateTag return valid response.")
	public void testCreateTag() throws Exception {
		tagControllerTest.testCreateTag();
		tag = tagControllerTest.tag;
	}



	@Test
	@Order(3)
	@DisplayName("03 - Should pass if the testCreateCategory return valid response.")
	public void testCreateCategory() throws Exception {
		categoryControllerTest.testCreateCategory();
		category = categoryControllerTest.category;
	}


	@Test
	@Order(4)
	@DisplayName("04 - Should pass if the testCreatePost return valid response.")
	public void testCreatePost() throws Exception {
		postControllerTest.testCreatePost();
		post = postControllerTest.post;
	}

	@Test
	@Order(5)
	@DisplayName("05 - Should pass if the testUpdatePostByPostRdbmsId return valid response.")
	public void testUpdatePostByPostRdbmsId() throws Exception {
		postControllerTest.testUpdatePostByPostRdbmsId();
	}


	@Test
	@Order(6)
	@DisplayName("06 - Should pass if the testUpdateAuthorByAuthorRdbmsId return valid response.")
	public void testUpdateAuthorByAuthorRdbmsId() throws Exception {
		authorControllerTest.testUpdateAuthorByAuthorRdbmsId();
	}


	@Test
	@Order(7)
	@DisplayName("07 - Should pass  if the testFindAuthorByAuthorRdbmsId return valid response.")
	public void testFindAuthorByAuthorRdbmsId() throws Exception {
		authorControllerTest.testFindAuthorByAuthorRdbmsId();
	}




	@Test
	@Order(8)
	@DisplayName("08- Should pass if the testUpdateByCategoryId return valid response.")
	public void testUpdateByCategoryId() throws Exception {
		categoryControllerTest.testUpdateByCategoryId();
	}


	@Test
	@Order(9)
	@DisplayName("09 - Should pass if the testFindAllCategories return valid response.")
	public void testFindAllCategories() throws Exception {
		categoryControllerTest.testFindAllCategories();
	}

	@Test
	@Order(10)
	@DisplayName("10 - Should pass  if the testFindByCategoryId return valid response.")
	public void testFindByCategoryRdbmsId() throws Exception {
		categoryControllerTest.testFindByCategoryRdbmsId();
	}

	@Test
	@Order(11)
	@DisplayName("11 -Should pass if the testFindByCategoryUrlName return valid response.")
	public void testFindByCategoryUrlName() throws Exception {
		categoryControllerTest.testFindByCategoryUrlName();
	}



	@Test
	@Order(12)
	@DisplayName("12 - Should pass if the testUpdateByTagRdbmsId return valid response.")
	public void testUpdateTagByTagRdbmsId() throws Exception {
		tagControllerTest.testUpdateTagByTagRdbmsId();
	}


	@Test
	@Order(13)
	@DisplayName("13 - Should pass if the testFindAllTags return valid response.")
	public void testFindAllTags() throws Exception {
		tagControllerTest.testFindAllTags();
	}

	@Test
	@Order(14)
	@DisplayName("14 - Should pass  if the testFindTagByTagRdbmsId return valid response.")
	public void testFindTagByTagRdbmsId() throws Exception {
		tagControllerTest.testFindTagByTagRdbmsId();
	}

	@Test
	@Order(15)
	@DisplayName("15 -Should pass if the testFindTagByTagUrlName return valid response.")
	public void testFindTagByTagUrlName() throws Exception {
		tagControllerTest.testFindTagByTagUrlName();
	}

	@Test
	@Order(16)
	@DisplayName("16 -Should pass if the testPostDeleteByPostRdbmsId return valid response.")
	public void testPostDeleteByPostRdbmsId() throws Exception {
		postControllerTest.testPostDeleteByPostRdbmsId();
	}

	@Test
	@Order(17)
	@DisplayName("17 -Should pass if the testDeleteTagByTagRdbmsId return valid response.")
	public void testDeleteTagByTagRdbmsId() throws Exception {
		tagControllerTest.testDeleteTagByTagRdbmsId();
	}

	@Test
	@Order(18)
	@DisplayName("18 -Should pass if the testDeleteAuthorByAuthorRdbmsId return valid response.")
	public void testDeleteAuthorByAuthorRdbmsId() throws Exception {
		authorControllerTest.testDeleteAuthorByAuthorRdbmsId();
	}


}
