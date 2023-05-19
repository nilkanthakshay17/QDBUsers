package com.qdb.app.users.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qdb.app.users.comm.PostsAndCommentsService;
import com.qdb.app.users.model.CommentRequestModel;
import com.qdb.app.users.model.CommentResponseModel;
import com.qdb.app.users.model.PostModel;
import com.qdb.app.users.model.PostRequestModel;
import com.qdb.app.users.model.PostResponseModel;
import com.qdb.app.users.service.CommentsServiceInt;
import com.qdb.app.users.service.PostsServiceInt;

@WebMvcTest(PostsAndCommentsController.class)
class PostsAndCommentsControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	private PostsServiceInt postsServiceInt;

	@MockBean
	private CommentsServiceInt commentsServiceInt;

	@MockBean
	private PostsAndCommentsService postsAndCommentsService;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	
	// posts local

	@Test
	public void testCreatePostLocal() throws Exception {
		String createPostURL = "/api/v1.0/posts/local/U999/F999";

		PostRequestModel postRequest = new PostRequestModel("Post1", "Post1 body");
		String inputJson = mapToJson(postRequest);
		PostResponseModel postResponse = new PostResponseModel("P999", "F999", "U999", "Post1", "Post1 body");

		when(postsServiceInt.createPost(any(), eq("U999"), eq("F999"))).thenReturn(postResponse);

		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.post(createPostURL).contentType(MediaType.APPLICATION_JSON).content(inputJson))
				.andReturn();

		assertNotNull(result);
		assertEquals(200, result.getResponse().getStatus());

		PostResponseModel receivedResponse = mapFromJson(result.getResponse().getContentAsString(),
				PostResponseModel.class);

		assertNotNull(receivedResponse);
		assertEquals("P999", receivedResponse.getPostId());
	}

	@Test
	public void testUpdatePostLocal() throws Exception {
		String updatePostURL = "/api/v1.0/posts/local/P999";

		PostRequestModel postRequest = new PostRequestModel("Post1", "Post1 body");
		String inputJson = mapToJson(postRequest);
		PostResponseModel postResponse = new PostResponseModel("P999", "F999", "U999", "Post1", "Post1 body");

		when(postsServiceInt.updatePost(any(PostRequestModel.class), eq("P999"))).thenReturn(postResponse);

		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.put(updatePostURL).contentType(MediaType.APPLICATION_JSON).content(inputJson))
				.andReturn();

		assertNotNull(result);
		assertEquals(202, result.getResponse().getStatus());

		PostResponseModel receivedResponse = mapFromJson(result.getResponse().getContentAsString(),
				PostResponseModel.class);

		assertNotNull(receivedResponse);
		assertEquals("P999", receivedResponse.getPostId());
	}

	@Test
	public void testGetPostByPostIdLocal() throws Exception {
		String getPostByPostIdURL = "/api/v1.0/posts/local/P999";

		PostResponseModel postResponse = new PostResponseModel("P999", "F999", "U999", "Post1", "Post1 body");

		when(postsServiceInt.getPostByPostId(eq("P999"))).thenReturn(postResponse);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get(getPostByPostIdURL).contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		assertNotNull(result);
		assertEquals(200, result.getResponse().getStatus());

		PostResponseModel receivedResponse = mapFromJson(result.getResponse().getContentAsString(),
				PostResponseModel.class);

		assertNotNull(receivedResponse);
		assertEquals("P999", receivedResponse.getPostId());
	}

	@Test
	public void testGetAllPostsLocal() throws Exception {
		String getAllPostsURL = "/api/v1.0/posts/local";

		PostResponseModel postResponse1 = new PostResponseModel("P999", "F999", "U999", "Post1", "Post1 body");
		PostResponseModel postResponse2 = new PostResponseModel("P998", "F998", "U998", "Post2", "Post2 body");

		List<PostResponseModel> postResponses = new ArrayList<>();
		postResponses.add(postResponse1);
		postResponses.add(postResponse2);

		when(postsServiceInt.getAllPosts()).thenReturn(postResponses);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get(getAllPostsURL).contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		assertNotNull(result);
		assertEquals(200, result.getResponse().getStatus());

		List<PostResponseModel> receivedResponse = mapFromJson(result.getResponse().getContentAsString(), List.class);

		assertNotNull(receivedResponse);
		assertEquals(2, receivedResponse.size());
	}

	@Test
	public void testDeletePostByPostIdLocal() throws Exception {
		String deletePostByPostIdURL = "/api/v1.0/posts/local/P999";

		PostResponseModel postResponse = new PostResponseModel("P999", "F999", "U999", "Post1", "Post1 body");

		when(postsServiceInt.deletePostByPostId(eq("P999"))).thenReturn(postResponse);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.delete(deletePostByPostIdURL).contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		assertNotNull(result);
		assertEquals(200, result.getResponse().getStatus());

		PostResponseModel receivedResponse = mapFromJson(result.getResponse().getContentAsString(),
				PostResponseModel.class);

		assertNotNull(receivedResponse);
		assertEquals("P999", receivedResponse.getPostId());
	}

	@Test
	public void testDeleteAllPostsLocal() throws Exception {
		String deleteAllPostsURL = "/api/v1.0/posts/local";

		when(postsServiceInt.deleteAllPosts()).thenReturn("Deleted All Posts");
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.delete(deleteAllPostsURL).contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		assertNotNull(result);
		assertEquals(200, result.getResponse().getStatus());
		assertEquals("Deleted All Posts", result.getResponse().getContentAsString());
	}

	// comments local

	@Test
	public void testCreateCommentLocal() throws Exception {
		String createCommentURL = "/api/v1.0/comments/local/U999/P999";

		CommentRequestModel commentRequest = new CommentRequestModel("Comment body");
		String inputJson = mapToJson(commentRequest);
		CommentResponseModel commentResponse = new CommentResponseModel("C999", "P999", "Comment1", "C@gmail.com",
				"Comment body");

		when(commentsServiceInt.createComment(any(), eq("U999"), eq("P999"))).thenReturn(commentResponse);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(createCommentURL)
				.contentType(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();

		assertNotNull(result);
		assertEquals(200, result.getResponse().getStatus());
		
		CommentResponseModel receivedResponse = mapFromJson(result.getResponse().getContentAsString(),
				CommentResponseModel.class);

		assertNotNull(receivedResponse);
		assertEquals("C999", receivedResponse.getCommentId());
	}

	@Test
	public void testUpdateCommentLocal() throws Exception {
		String updateCommentURL = "/api/v1.0/comments/local/C999";

		CommentRequestModel commentRequest = new CommentRequestModel("Comment2 body");
		String inputJson = mapToJson(commentRequest);
		CommentResponseModel commentResponse = new CommentResponseModel("C999", "P999", "Comment1", "C@gmail.com",
				"Comment2 body");

		when(commentsServiceInt.updateComment(any(CommentRequestModel.class), eq("C999"))).thenReturn(commentResponse);

		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.put(updateCommentURL).contentType(MediaType.APPLICATION_JSON).content(inputJson))
				.andReturn();

		assertNotNull(result);
		assertEquals(202, result.getResponse().getStatus());
		
		CommentResponseModel receivedResponse = mapFromJson(result.getResponse().getContentAsString(),
				CommentResponseModel.class);

		assertNotNull(receivedResponse);
		assertEquals("C999", receivedResponse.getCommentId());
	}

	@Test
	public void testGetCommentByCommentIdLocal() throws Exception {
		String getCommentByCommentIdURL = "/api/v1.0/comments/local/C999";

		CommentResponseModel commentResponse = new CommentResponseModel("C999", "P999", "Comment1", "C@gmail.com",
				"Comment2 body");

		when(commentsServiceInt.getCommentByCommentId(eq("C999"))).thenReturn(commentResponse);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get(getCommentByCommentIdURL).contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		assertNotNull(result);
		assertEquals(200, result.getResponse().getStatus());
		
		CommentResponseModel receivedResponse = mapFromJson(result.getResponse().getContentAsString(),
				CommentResponseModel.class);

		assertNotNull(receivedResponse);
		assertEquals("C999", receivedResponse.getCommentId());
	}

	@Test
	public void testGetAllCommentsLocal() throws Exception {
		String getAllCommentsURL = "/api/v1.0/comments/local";

		CommentResponseModel commentResponse1 = new CommentResponseModel("C999", "P999", "Comment1", "C@gmail.com",
				"Comment2 body");
		CommentResponseModel commentResponse2 = new CommentResponseModel("C998", "P998", "Comment2", "C2@gmail.com",
				"Comment2 body");

		List<CommentResponseModel> commentResponses = new ArrayList<>();
		commentResponses.add(commentResponse1);
		commentResponses.add(commentResponse2);

		when(commentsServiceInt.getAllComments()).thenReturn(commentResponses);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get(getAllCommentsURL).contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		assertNotNull(result);
		assertEquals(200, result.getResponse().getStatus());
		
		List<CommentResponseModel> receivedResponse = mapFromJson(result.getResponse().getContentAsString(),
				List.class);

		assertNotNull(receivedResponse);
		assertEquals(2,receivedResponse.size());
	}

	@Test
	public void testDeleteCommentByCommentIdLocal() throws Exception {
		String deleteCommentByCommentIdURL = "/api/v1.0/comments/local/C999";

		CommentResponseModel commentResponse = new CommentResponseModel("C999", "P999", "Comment1", "C@gmail.com",
				"Comment2 body");

		when(commentsServiceInt.deleteCommentByCommentId(eq("C999"))).thenReturn(commentResponse);

		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.delete(deleteCommentByCommentIdURL).contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		assertNotNull(result);
		assertEquals(200, result.getResponse().getStatus());
	
		CommentResponseModel receivedResponse = mapFromJson(result.getResponse().getContentAsString(),
				CommentResponseModel.class);

		assertNotNull(receivedResponse);
		assertEquals("C999", receivedResponse.getCommentId());
	}

	@Test
	public void testDeleteAllCommentsLocal() throws Exception {
		String deleteAllCommentsURL = "/api/v1.0/comments/local";

		when(commentsServiceInt.deleteAllComments()).thenReturn("Deleted All Comments");
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.delete(deleteAllCommentsURL).contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		assertNotNull(result);
		assertEquals(200, result.getResponse().getStatus());
		assertEquals("Deleted All Comments", result.getResponse().getContentAsString());
	}

	protected String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}
}
