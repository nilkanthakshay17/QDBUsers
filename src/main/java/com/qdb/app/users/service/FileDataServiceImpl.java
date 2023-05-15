package com.qdb.app.users.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.qdb.app.users.entity.FileDataEntity;
import com.qdb.app.users.entity.UserEntity;
import com.qdb.app.users.repository.FileDataRepository;
import com.qdb.app.users.repository.UsersRepository;
import com.qdb.app.users.util.FileDataUtil;

@Service
public class FileDataServiceImpl implements FileDataServiceInt{
	
	@Autowired
	private FileDataRepository fileDataRepository;
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Override
	public String uploadFile(String userId,MultipartFile file) throws IOException{
		FileDataEntity fde=new FileDataEntity();
		
		fde.setFileId(UUID.randomUUID().toString());
		
		fde.setName(file.getOriginalFilename());
		fde.setType(file.getContentType());
		fde.setFileData(FileDataUtil.compressFile(file.getBytes()));
		
		Optional<UserEntity> user = usersRepository.findByUserId(userId);
		
		user.get().getFiles().add(fde);
		
		
		FileDataEntity fileData= fileDataRepository.save(fde);

		
		UserEntity savedUser = usersRepository.save(user.get());
		
		
		if(null != fileData) {
			return "File uploaded successfully :"+file.getOriginalFilename();
		}
		return "File uploading failed";
	}
	
	@Override
	public byte[] downloadFile(String fileId) throws IOException{
		Optional<FileDataEntity> dbFileData=fileDataRepository.findByFileId(fileId);
		byte[] fileData=FileDataUtil.decompressFile(dbFileData.get().getFileData());
		return fileData;
	}
	
	@Override
	public FileDataEntity deleteFileByFileId(String fileId) throws Exception {
		Optional<FileDataEntity> dbFileData=fileDataRepository.findByFileId(fileId);
		fileDataRepository.deleteById(dbFileData.get().getFid());
		return dbFileData.get();
	}
	
	@Override
	public List<FileDataEntity> getAllFiles() throws Exception {
		return fileDataRepository.findAll();
	}
}
