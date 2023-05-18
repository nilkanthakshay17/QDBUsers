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

import com.qdb.app.users.entity.FileDataEntity;
import com.qdb.app.users.entity.PostEntity;
import com.qdb.app.users.model.PostRequestModel;
import com.qdb.app.users.model.PostResponseModel;
import com.qdb.app.users.repository.FileDataRepository;
import com.qdb.app.users.repository.PostsRepository;

@SpringBootTest
class PostsServiceImplTest {

	@Autowired
	private PostsServiceImpl postsServiceImpl;
	
	@MockBean
	private PostsRepository postsRepository;
	
	@MockBean
	private FileDataRepository fileDataRepository;
	
	
	@MockBean
	ModelMapper modelMapper;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	public void testGetAllPosts() {
		PostEntity postEntity1 = new PostEntity("P999", "F999", "U999", "Post 1", "Post 1 Body");
		PostEntity postEntity2 = new PostEntity("P999", "F99", "U999", "Post 1", "Post 1 Body");
		List<PostEntity> postEntities = new ArrayList<>();
		postEntities.add(postEntity1);
		postEntities.add(postEntity2);
		
		PostResponseModel postResponse = new PostResponseModel("P999", "F999", "U999", "Post 1", "Post 1 Body");
		
		List<PostResponseModel> postResponses = new ArrayList<>();
		postResponses.add(postResponse);
		postResponses.add(postResponse);
		
		when(postsRepository.findAll()).thenReturn(postEntities);
		when(modelMapper.map(any(), eq(PostResponseModel.class))).thenReturn(postResponse);
		
		List<PostResponseModel> receivedResponses = postsServiceImpl.getAllPosts();
		
		assertNotNull(receivedResponses);
		assertEquals(2, receivedResponses.size());
		
	}

	@Test
	public void testGetPostByPostId() {
		PostEntity postEntity = new PostEntity("P999", "F99", "U999", "Post 1", "Post 1 Body");
		PostResponseModel postResponse = new PostResponseModel("P999", "F999", "U999", "Post 1", "Post 1 Body");
		
		when(postsRepository.findByPostId(eq("P999"))).thenReturn(postEntity);
		when(modelMapper.map(any(), eq(PostResponseModel.class))).thenReturn(postResponse);
		
		PostResponseModel receivedResponse = postsServiceImpl.getPostByPostId("P999");
		
		assertNotNull(receivedResponse);
		
	}

	@Test
	public void testCreatePost() {
		PostRequestModel postRequest = new PostRequestModel("P1 title", "P1 Body");
		PostResponseModel postResponse = new PostResponseModel("P999", "F999", "U999", "P1 Title", "P1 Body");
		PostEntity postEntity = new PostEntity("P999", "F99", "U999", "Post 1", "Post 1 Body");
		FileDataEntity fileEntity = new FileDataEntity("F999", "File 1", "pdf", null, null);
		
		when(fileDataRepository.findByFileId(eq("F999"))).thenReturn(Optional.of(fileEntity));
		when(fileDataRepository.save(fileEntity)).thenReturn(fileEntity);
		when(postsRepository.save(eq(postEntity))).thenReturn(postEntity);
		when(modelMapper.map(any(), eq(PostResponseModel.class))).thenReturn(postResponse);
		
		PostResponseModel receivedResponse = postsServiceImpl.createPost(postRequest,"U999","F999");
		
		assertNotNull(receivedResponse);
	}

	@Test
	public void testUpdatePost() {
		PostRequestModel postRequest = new PostRequestModel("P1 title", "P1 Body");
		PostEntity postEntity = new PostEntity("P999", "F99", "U999", "Post 1", "Post 1 Body");
		PostResponseModel postResponse = new PostResponseModel("P999", "F999", "U999", "P1 Title", "P1 Body");

		when(postsRepository.findByPostId(eq("P999"))).thenReturn(postEntity);
		when(postsRepository.save(eq(postEntity))).thenReturn(postEntity);
		when(modelMapper.map(any(), eq(PostResponseModel.class))).thenReturn(postResponse);
		
		PostResponseModel receivedResponse = postsServiceImpl.updatePost(postRequest, "P999");

		assertNotNull(receivedResponse);
	}

	@Test
	public void testDeletePostByPostId() {
		PostEntity postEntity = new PostEntity("P999", "F99", "U999", "Post 1", "Post 1 Body");
		FileDataEntity fileEntity = new FileDataEntity("F999", "File 1", "pdf", null, null);
		postEntity.setFile(fileEntity);
		FileDataEntity updatedfileEntity = new FileDataEntity("F999", "File 1", "pdf", null, null);
		PostResponseModel postResponse = new PostResponseModel("P999", "F999", "U999", "P1 Title", "P1 Body");
		
		when(postsRepository.findByPostId(eq("P999"))).thenReturn(postEntity);
		when(fileDataRepository.findByFileId(eq("F999"))).thenReturn(Optional.of(fileEntity));
		when(fileDataRepository.save(eq(fileEntity))).thenReturn(updatedfileEntity);
		when(modelMapper.map(any(), eq(PostResponseModel.class))).thenReturn(postResponse);
		
		PostResponseModel receivedResponse = postsServiceImpl.deletePostByPostId("P999");
		
		assertNotNull(receivedResponse);
	}

	@Test
	public void testDeleteAllPosts() {

		String receivedResponse = postsServiceImpl.deleteAllPosts();
		
		assertNotNull(receivedResponse);
		assertEquals("Deleted All Posts", receivedResponse);
	}

}
