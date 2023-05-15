package com.qdb.app.users.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qdb.app.users.entity.FileDataEntity;

@Repository
public interface FileDataRepository extends JpaRepository<FileDataEntity, Integer>{
	
	Optional<FileDataEntity> findByName(String name);
	Optional<FileDataEntity> findByFileId(String fileId);
}
