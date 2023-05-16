package com.qdb.app.users.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.qdb.app.users.entity.CommentEntity;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommentsRepositoryTest {

	@Autowired
	private CommentsRepository commentsRepository;
	
	CommentEntity commentEntity;
	
	@BeforeEach
	void setUp() throws Exception {
		commentEntity = new CommentEntity("999", "999", "abc", "abc@gmail.com", "comment body");
		commentsRepository.save(commentEntity);
	}

	@AfterEach
	void tearDown() throws Exception {
		commentsRepository.delete(commentEntity);
	}

	@Test
	public void testFindByCommentId_success() {
		CommentEntity receivedCommentEntity = commentsRepository.findByCommentId("999");
		
		assertNotNull(receivedCommentEntity);
		assertEquals("999", receivedCommentEntity.getCommentId());
		assertEquals("999", receivedCommentEntity.getPostId());
		assertEquals("abc", receivedCommentEntity.getName());
		assertEquals("abc@gmail.com", receivedCommentEntity.getEmail());
		assertEquals("comment body", receivedCommentEntity.getBody());
	}

	@Test
	public void testFindByCommentId_failure() {
		CommentEntity receivedCommentEntity = commentsRepository.findByCommentId("111");
		
		assertNull(receivedCommentEntity);
	}
}
