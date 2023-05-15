package com.qdb.app.users.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.qdb.app.users.entity.FileDataEntity;

@Service
public interface FileDataServiceInt {
	
	public String uploadFile(String userId,MultipartFile file) throws IOException;
	public byte[] downloadFile(String name) throws IOException;
	public FileDataEntity deleteFileByFileId(String fileId) throws Exception;
	public List<FileDataEntity> getAllFiles() throws Exception;
	
}
