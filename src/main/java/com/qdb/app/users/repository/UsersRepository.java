package com.qdb.app.users.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qdb.app.users.entity.UserEntity;

@Repository
public interface UsersRepository extends JpaRepository<UserEntity, Integer>{
	
	public Optional<UserEntity> findByUserId(String userId);
}