package com.qdb.app.users.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qdb.app.users.model.PostRequestModel;
import com.qdb.app.users.model.PostResponseModel;

@SpringBootTest
class PostsServiceIntTest {

	@MockBean
	private PostsServiceInt postsServiceInt;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	public void testGetAllPosts() {
		PostResponseModel postResponse1 = new PostResponseModel("P998", "F998", "U998", "P Title", "P Body");
		PostResponseModel postResponse2 = new PostResponseModel("P999", "F999", "U999", "P1 Title", "P1 Body");
		List<PostResponseModel> postResponses = new ArrayList<>();
		postResponses.add(postResponse1);
		postResponses.add(postResponse2);
		
		when(postsServiceInt.getAllPosts()).thenReturn(postResponses);
		
		List<PostResponseModel> receivedResponses = postsServiceInt.getAllPosts();
		
		assertNotNull(receivedResponses);
		assertEquals(2, receivedResponses.size());
	}

	@Test
	public void testGetPostByPostId() {
		
		PostResponseModel postResponse = new PostResponseModel("P999", "F999", "U999", "P1 Title", "P1 Body");

		when(postsServiceInt.getPostByPostId(eq("P999"))).thenReturn(postResponse);

		PostResponseModel receivedResponse = postsServiceInt.getPostByPostId("P999");

		assertNotNull(receivedResponse);
		assertEquals("P999", receivedResponse.getPostId());
		assertEquals("F999", receivedResponse.getFileId());
		assertEquals("U999", receivedResponse.getUserId());
		assertEquals("P1 Title", receivedResponse.getTitle());
		assertEquals("P1 Body", receivedResponse.getBody());
	}

	@Test
	public void testCreatePost() {
		PostRequestModel postRequest = new PostRequestModel("P1 title", "P1 Body");

		PostResponseModel postResponse = new PostResponseModel("P999", "F999", "U999", "P1 Title", "P1 Body");

		when(postsServiceInt.createPost(eq(postRequest), eq("U999"), eq("F999"))).thenReturn(postResponse);

		PostResponseModel receivedResponse = postsServiceInt.createPost(postRequest, "U999", "F999");

		assertNotNull(receivedResponse);
		assertEquals("P999", receivedResponse.getPostId());
		assertEquals("F999", receivedResponse.getFileId());
		assertEquals("U999", receivedResponse.getUserId());
		assertEquals("P1 Title", receivedResponse.getTitle());
		assertEquals("P1 Body", receivedResponse.getBody());

	}

	@Test
	public void testUpdatePost() {
		PostRequestModel postRequest = new PostRequestModel("P1 title", "P1 Body");

		PostResponseModel postResponse = new PostResponseModel("P999", "F999", "U999", "P1 Title", "P1 Body");

		when(postsServiceInt.updatePost(eq(postRequest), eq("P999"))).thenReturn(postResponse);

		PostResponseModel receivedResponse = postsServiceInt.updatePost(postRequest, "P999");

		assertNotNull(receivedResponse);
		assertEquals("P999", receivedResponse.getPostId());
		assertEquals("F999", receivedResponse.getFileId());
		assertEquals("U999", receivedResponse.getUserId());
		assertEquals("P1 Title", receivedResponse.getTitle());
		assertEquals("P1 Body", receivedResponse.getBody());

	}

	@Test
	public void testDeletePostByPostId() {
		PostResponseModel postResponse = new PostResponseModel("P999", "F999", "U999", "P1 Title", "P1 Body");

		when(postsServiceInt.deletePostByPostId(eq("P999"))).thenReturn(postResponse);

		PostResponseModel receivedResponse = postsServiceInt.deletePostByPostId("P999");

		assertNotNull(receivedResponse);
		assertEquals("P999", receivedResponse.getPostId());
		assertEquals("F999", receivedResponse.getFileId());
		assertEquals("U999", receivedResponse.getUserId());
		assertEquals("P1 Title", receivedResponse.getTitle());
		assertEquals("P1 Body", receivedResponse.getBody());
	}

	@Test
	public void testDeleteAllPosts() {
		when(postsServiceInt.deleteAllPosts()).thenReturn("Deleted All Posts");

		String receivedResponse = postsServiceInt.deleteAllPosts();

		assertNotNull(receivedResponse);
		assertEquals("Deleted All Posts", receivedResponse);
	}

}
