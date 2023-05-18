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

import com.qdb.app.users.model.CommentRequestModel;
import com.qdb.app.users.model.CommentResponseModel;

@SpringBootTest
class CommentsServiceIntTest {

	@MockBean
	private CommentsServiceInt commentsServiceInt;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}


	@Test
	public void testGetAllComments() {
		CommentResponseModel commentResponse1 = new CommentResponseModel("C998", "P998", "User1", "U1@gmail.com", "Comment1 body");
		CommentResponseModel commentResponse2 = new CommentResponseModel("C999", "P999", "User2", "U2@gmail.com", "Comment2 body");
		List<CommentResponseModel> commentResponses = new ArrayList<>();
		commentResponses.add(commentResponse1);
		commentResponses.add(commentResponse2);
		
		when(commentsServiceInt.getAllComments()).thenReturn(commentResponses);
		
		List<CommentResponseModel> receivedResponses = commentsServiceInt.getAllComments();
		
		assertNotNull(receivedResponses);
		assertEquals(2, receivedResponses.size());
	}
	
	@Test
	public void testGetCommentByCommentId() {
		CommentResponseModel commentResponse = new CommentResponseModel("C999", "P999", "User1", "U@gmail.com", "Comment body");
	
		when(commentsServiceInt.getCommentByCommentId(eq("C999"))).thenReturn(commentResponse);
		
		CommentResponseModel receivedResponse = commentsServiceInt.getCommentByCommentId("C999");
		
		assertNotNull(receivedResponse);
		assertEquals("C999", receivedResponse.getCommentId());
		assertEquals("P999", receivedResponse.getPostId());
		assertEquals("User1", receivedResponse.getName());
		assertEquals("U@gmail.com", receivedResponse.getEmail());
		assertEquals("Comment body", receivedResponse.getBody());
	}
	
	@Test
	public void testCreateComment() {
		CommentRequestModel commentRequest = new CommentRequestModel("Comment body");
		CommentResponseModel commentResponse = new CommentResponseModel("C999", "P999", "User1", "U@gmail.com", "Comment body");
		when(commentsServiceInt.createComment(eq(commentRequest), eq("U999"), eq("P999"))).thenReturn(commentResponse);
		
		CommentResponseModel receivedResponse = commentsServiceInt.createComment(commentRequest, "U999", "P999");
		
		assertNotNull(receivedResponse);
		assertEquals("C999", receivedResponse.getCommentId());
		assertEquals("P999", receivedResponse.getPostId());
		assertEquals("User1", receivedResponse.getName());
		assertEquals("U@gmail.com", receivedResponse.getEmail());
		assertEquals("Comment body", receivedResponse.getBody());
	}
	
	@Test
	public void testUpdateComment() {
		CommentRequestModel commentRequest = new CommentRequestModel("Comment body");
		CommentResponseModel commentResponse = new CommentResponseModel("C999", "P999", "User1", "U@gmail.com", "Comment body");
		when(commentsServiceInt.updateComment(eq(commentRequest), eq("C999"))).thenReturn(commentResponse);
		
		CommentResponseModel receivedResponse = commentsServiceInt.updateComment(commentRequest,"C999");
		
		assertNotNull(receivedResponse);
		assertEquals("C999", receivedResponse.getCommentId());
		assertEquals("P999", receivedResponse.getPostId());
		assertEquals("User1", receivedResponse.getName());
		assertEquals("U@gmail.com", receivedResponse.getEmail());
		assertEquals("Comment body", receivedResponse.getBody());
	}
	
	@Test
	public void testDeleteCommentByCommentId() {
		CommentResponseModel commentResponse = new CommentResponseModel("C999", "P999", "User1", "U@gmail.com", "Comment body");
		
		when(commentsServiceInt.deleteCommentByCommentId(eq("C999"))).thenReturn(commentResponse);
		
		CommentResponseModel receivedResponse = commentsServiceInt.deleteCommentByCommentId("C999");
		
		assertNotNull(receivedResponse);
		assertEquals("C999", receivedResponse.getCommentId());
		assertEquals("P999", receivedResponse.getPostId());
		assertEquals("User1", receivedResponse.getName());
		assertEquals("U@gmail.com", receivedResponse.getEmail());
		assertEquals("Comment body", receivedResponse.getBody());
	}
	
	@Test
	public void testDeleteAllComments() {
		when(commentsServiceInt.deleteAllComments()).thenReturn("Deleted All Comments");
		
		String receivedResponse = commentsServiceInt.deleteAllComments();
		
		assertNotNull(receivedResponse);
		assertEquals("Deleted All Comments", receivedResponse);
	}
}
