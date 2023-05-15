/**
 * 
 */
package com.qdb.app.users.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.qdb.app.users.entity.UserEntity;

/**
 * @author Akshay
 *
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UsersRepositoryTest {
	
	@Autowired
	private UsersRepository usersRepository;
	
	UserEntity userEntity;
	
	@BeforeEach
	void setUp() throws Exception {
		userEntity = new UserEntity(1, "999", "Akshay999", "Akshay@gmail.com", "Akshay@123");
		usersRepository.save(userEntity);
	}
	
	@AfterEach
	public void cleanup() {
		usersRepository.delete(userEntity);
	}

	@Test
	public void testFindByUserId_success() {
		Optional<UserEntity> receivedEntity = usersRepository.findByUserId("999");
		assertNotNull(receivedEntity.get());
		assertEquals("999", receivedEntity.get().getUserId());
		assertEquals("Akshay999", receivedEntity.get().getUserName());
		assertEquals("Akshay@gmail.com", receivedEntity.get().getEmail());
		assertEquals("Akshay@123", receivedEntity.get().getEncryptedPassword());
	}

	@Test
	public void testFindByUserId_failure() {
		Optional<UserEntity> receivedEntity = usersRepository.findByUserId("111");
		assertEquals(Optional.empty(),receivedEntity);
	}
	
}
