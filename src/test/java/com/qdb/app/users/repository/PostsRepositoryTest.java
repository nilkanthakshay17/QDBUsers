package com.qdb.app.users.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.qdb.app.users.entity.CommentEntity;
import com.qdb.app.users.entity.PostEntity;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PostsRepositoryTest {

	@Autowired
	private PostsRepository postsRepository;
	
	@Autowired
	private CommentsRepository commentsRepository;
	
	PostEntity postEntity;
	CommentEntity commentEntity;
	List<CommentEntity> comments;
	
	@BeforeEach
	void setUp() throws Exception {
		postEntity = new PostEntity("999", "9993", "999", "Post 1", "Post 1 Body");
		commentEntity = new CommentEntity("999", "999", "abc", "abc@gmail.com", "comment body");
		postEntity.addComment(commentEntity);
		postsRepository.save(postEntity);
	}

	@AfterEach
	void tearDown() throws Exception {
		postsRepository.delete(postEntity);
	}

	@Test
	public void testFindByUserId_success() {
		PostEntity receivedEntity = postsRepository.findByPostId("999");
		assertNotNull(receivedEntity);
		assertEquals("999", receivedEntity.getPostId());
		assertEquals("9993", receivedEntity.getFileId());
		assertEquals("999", receivedEntity.getUserId());
		assertEquals("Post 1", receivedEntity.getTitle());
		assertEquals("Post 1 Body", receivedEntity.getBody());
		assertNotNull(receivedEntity.getComments());
	}

	@Test
	public void testFindByUserId_failure() {
		PostEntity receivedEntity = postsRepository.findByPostId("111");
		assertNull(receivedEntity);
	}
}
