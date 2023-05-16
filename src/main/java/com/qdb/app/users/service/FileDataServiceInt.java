package com.qdb.app.users.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.qdb.app.users.model.FileDataResponseModel;

@Service
public interface FileDataServiceInt {
	
	public FileDataResponseModel uploadFile(String userId,MultipartFile file) throws IOException;
	public byte[] downloadFile(String name) throws IOException;
	public FileDataResponseModel deleteFileByFileId(String fileId) throws Exception;
	public List<FileDataResponseModel> getAllFiles() throws Exception;
	
}
