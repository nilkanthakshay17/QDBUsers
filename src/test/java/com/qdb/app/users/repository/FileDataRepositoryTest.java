package com.qdb.app.users.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.qdb.app.users.entity.FileDataEntity;
import com.qdb.app.users.entity.PostEntity;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FileDataRepositoryTest {

	@Autowired
	private FileDataRepository fileDataRepository;
	
	FileDataEntity fileDataEntity;
	PostEntity postEntity;
	
	@BeforeEach
	void setUp() throws Exception {
		fileDataEntity = new FileDataEntity("999", "file1", "pdf", null, null);
		postEntity = new PostEntity("999", "99", "99", "Post1", "Post1 Body");
		fileDataEntity.setPost(postEntity);
		fileDataRepository.save(fileDataEntity);
	}

	@AfterEach
	void tearDown() throws Exception {
		fileDataRepository.delete(fileDataEntity);
	}

	@Test
	public void testFindByFileId_success() {
		Optional<FileDataEntity> receivedFileDataEntity = fileDataRepository.findByFileId("999");
		assertNotNull(receivedFileDataEntity.get());
		assertEquals("999", receivedFileDataEntity.get().getFileId());
		assertEquals("file1", receivedFileDataEntity.get().getName());
		assertEquals("pdf", receivedFileDataEntity.get().getType());
		assertEquals("999", receivedFileDataEntity.get().getPost().getPostId());
		assertEquals("99", receivedFileDataEntity.get().getPost().getFileId());
		assertEquals("99", receivedFileDataEntity.get().getPost().getUserId());
		assertEquals("Post1", receivedFileDataEntity.get().getPost().getTitle());
		assertEquals("Post1 Body", receivedFileDataEntity.get().getPost().getBody());
	}


	@Test
	public void testFindByFileId_failure() {
		Optional<FileDataEntity> receivedFileDataEntity = fileDataRepository.findByFileId("1");
		assertEquals(Optional.empty(), receivedFileDataEntity);
		
	}
}
