package com.qdb.app.users.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qdb.app.users.entity.CommentEntity;
import com.qdb.app.users.entity.PostEntity;
import com.qdb.app.users.entity.UserEntity;
import com.qdb.app.users.model.CommentRequestModel;
import com.qdb.app.users.model.CommentResponseModel;
import com.qdb.app.users.repository.CommentsRepository;
import com.qdb.app.users.repository.PostsRepository;
import com.qdb.app.users.repository.UsersRepository;

@SpringBootTest
class CommentsServiceImplTest {

	@Autowired
	private CommentsServiceImpl commentsServiceImpl;
	
	@MockBean
	private CommentsRepository commentsRepository;
	
	@MockBean
	private PostsRepository postsRepository;
	
	@MockBean
	private UsersRepository usersRepository;
	
	@MockBean
	ModelMapper modelMapper;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	
	@Test
	public void testGetAllComments() {
		CommentEntity commentEntity1 = new CommentEntity("C999", "P999", "Comment1", "C@gmail.com", "Comment1 body"); 
		CommentEntity commentEntity2 = new CommentEntity("C999", "P999", "Comment1", "C@gmail.com", "Comment1 body");
		
		List<CommentEntity> commentEntities = new ArrayList<>();
		commentEntities.add(commentEntity1);
		commentEntities.add(commentEntity2);
		
		CommentResponseModel commentResponse = new CommentResponseModel("C998", "P998", "User1", "U1@gmail.com", "Comment1 body");
		
		List<CommentResponseModel> commentResponses = new ArrayList<>();
		commentResponses.add(commentResponse);
		commentResponses.add(commentResponse);
		
		when(commentsRepository.findAll()).thenReturn(commentEntities);
		when(modelMapper.map(any(), eq(CommentResponseModel.class))).thenReturn(commentResponse);
		
		List<CommentResponseModel> receivedResponses = commentsServiceImpl.getAllComments();
		
		assertNotNull(receivedResponses);
		assertEquals(2, receivedResponses.size());
	}
	
	@Test
	public void testGetCommentByCommentId() {
		CommentEntity commentEntity = new CommentEntity("C999", "P999", "Comment1", "C@gmail.com", "Comment1 body");
		CommentResponseModel commentResponse = new CommentResponseModel("C999", "P999", "User1", "U@gmail.com", "Comment body");
	
		when(commentsRepository.findByCommentId(eq("C999"))).thenReturn(commentEntity);
		when(modelMapper.map(any(), eq(CommentResponseModel.class))).thenReturn(commentResponse);
		
		CommentResponseModel receivedResponse = commentsServiceImpl.getCommentByCommentId("C999");
		
		
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
		
		CommentEntity commentEntity = new CommentEntity("C999", "P999", "User1", "U@gmail.com", "Comment body");
		UserEntity userEntity = new UserEntity(1, "U999", "Akshay", "A@gmail.com", "A@123");
		PostEntity postEntity = new PostEntity("P999", "F999", "U999", "Post1", "Post1 body");
		
		CommentResponseModel commentResponse = new CommentResponseModel("C999", "P999", "User1", "U@gmail.com", "Comment body");
	
		when(usersRepository.findByUserId(eq("U999"))).thenReturn(Optional.of(userEntity));
		when(postsRepository.findByPostId("P999")).thenReturn(postEntity);
		when(commentsRepository.save(commentEntity)).thenReturn(commentEntity);
		when(postsRepository.save(postEntity)).thenReturn(postEntity);
		when(modelMapper.map(any(), eq(CommentResponseModel.class))).thenReturn(commentResponse);
	
		
		CommentResponseModel receivedResponse = commentsServiceImpl.createComment(commentRequest, "U999", "P999");
		
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
	
		CommentEntity commentEntity = new CommentEntity("C999", "P999", "User1", "U@gmail.com", "Comment body");
		
		CommentResponseModel commentResponse = new CommentResponseModel("C999", "P999", "User1", "U@gmail.com", "Comment body");
		
		when(commentsRepository.findByCommentId(eq("C999"))).thenReturn(commentEntity);
		when(commentsRepository.save(commentEntity)).thenReturn(commentEntity);
		when(modelMapper.map(any(), eq(CommentResponseModel.class))).thenReturn(commentResponse);
		
		CommentResponseModel receivedResponse = commentsServiceImpl.updateComment(commentRequest, "C999");
		
		assertNotNull(receivedResponse);

		assertEquals("C999", receivedResponse.getCommentId());
		assertEquals("P999", receivedResponse.getPostId());
		assertEquals("User1", receivedResponse.getName());
		assertEquals("U@gmail.com", receivedResponse.getEmail());
		assertEquals("Comment body", receivedResponse.getBody());
	}
	
	@Test
	public void testDeleteCommentByCommentId() {
		CommentEntity commentEntity1 = new CommentEntity("C999", "P999", "Comment1", "C@gmail.com", "Comment1 body");
		CommentEntity commentEntity2 = new CommentEntity("C998", "P998", "Comment2", "C2@gmail.com", "Comment2 body");
		
		PostEntity postEntity1 = new PostEntity("P999", "F999", "U999", "Post1", "Post1 body");
		postEntity1.addComment(commentEntity1);
		postEntity1.addComment(commentEntity2);
		commentEntity1.setPost(postEntity1);
		commentEntity2.setPost(postEntity1);
		
		CommentEntity commentEntity3 = new CommentEntity("C998", "P998", "Comment2", "C2@gmail.com", "Comment2 body");
		PostEntity postEntity2 = new PostEntity("P998", "F998", "U998", "Post2", "Post2 body");
		postEntity2.addComment(commentEntity3);
		commentEntity3.setPost(postEntity2);
		
		CommentResponseModel commentResponse = new CommentResponseModel("C998", "P998", "User2", "U2@gmail.com", "Comment2 body");
		
		when(commentsRepository.findByCommentId(eq("C999"))).thenReturn(commentEntity1);
		when(postsRepository.findByPostId("P999")).thenReturn(postEntity1);
		when(postsRepository.save(postEntity1)).thenReturn(postEntity2);
		when(modelMapper.map(any(), eq(CommentResponseModel.class))).thenReturn(commentResponse);
		
		CommentResponseModel receivedResponse = commentsServiceImpl.deleteCommentByCommentId("C999");
		
		assertNotNull(receivedResponse);
		assertEquals("C998", receivedResponse.getCommentId());
		assertEquals("P998", receivedResponse.getPostId());
		assertEquals("User2", receivedResponse.getName());
		assertEquals("U2@gmail.com", receivedResponse.getEmail());
		assertEquals("Comment2 body", receivedResponse.getBody());
	}
	
	@Test
	public void testDeleteAllComments() {
		
		String receivedResponse = commentsServiceImpl.deleteAllComments();
		
		assertNotNull(receivedResponse);
		assertEquals("Deleted All Comments", receivedResponse);
	}
}
