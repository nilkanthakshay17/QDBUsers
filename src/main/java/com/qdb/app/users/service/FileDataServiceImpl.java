package com.qdb.app.users.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.qdb.app.users.entity.FileDataEntity;
import com.qdb.app.users.entity.UserEntity;
import com.qdb.app.users.exception.FileDataException;
import com.qdb.app.users.exception.UserException;
import com.qdb.app.users.model.FileDataResponseModel;
import com.qdb.app.users.repository.FileDataRepository;
import com.qdb.app.users.repository.UsersRepository;
import com.qdb.app.users.util.FileDataUtil;

@Service
public class FileDataServiceImpl implements FileDataServiceInt{
	
	@Autowired
	private FileDataRepository fileDataRepository;
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public FileDataServiceImpl() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}
	
	
	@Override
	public FileDataResponseModel uploadFile(String userId,MultipartFile file) throws IOException{
		if(null == file && ! file.getContentType().equals(MediaType.valueOf("application/pdf"))) {
			throw new FileDataException("File type should be .pdf");
		}
		
		FileDataEntity fde=new FileDataEntity();
		
		fde.setFileId(UUID.randomUUID().toString());
		fde.setName(file.getOriginalFilename());
		fde.setType(file.getContentType());
		fde.setFileData(FileDataUtil.compressFile(file.getBytes()));
		
		Optional<UserEntity> user = usersRepository.findByUserId(userId);
		
		if(null == user || user.equals(Optional.empty())) {
			throw new UserException("User not found");
		}
		
		UserEntity userEntity = user.get();
		userEntity.addFile(fde);
		
		UserEntity savedUserEntity = usersRepository.save(userEntity);
		
		if(null == savedUserEntity) {
			throw new FileDataException("Uploading failed");
		}
		
		FileDataResponseModel fdeResponse = modelMapper.map(fde, FileDataResponseModel.class);
		
		return fdeResponse;
		
	}
	
	@Override
	public byte[] downloadFile(String fileId) throws IOException{
		Optional<FileDataEntity> dbFileData=fileDataRepository.findByFileId(fileId);
		
		if(null == dbFileData || dbFileData.equals(Optional.empty())) {
			throw new FileDataException("File not found");
		}
		
		byte[] fileData=FileDataUtil.decompressFile(dbFileData.get().getFileData());
		return fileData;
	}
	
	@Override
	public FileDataResponseModel deleteFileByFileId(String fileId) throws Exception {
		Optional<FileDataEntity> dbFileData=fileDataRepository.findByFileId(fileId);
		
		if(null == dbFileData || dbFileData.equals(Optional.empty())) {
			throw new FileDataException("File not found");
		}
		
		fileDataRepository.delete(dbFileData.get());
		
		FileDataResponseModel fdeResponseModel = modelMapper.map(dbFileData.get(), FileDataResponseModel.class);
		
		return fdeResponseModel;
	}
	
	@Override
	public List<FileDataResponseModel> getAllFiles() throws Exception {
		List<FileDataEntity> allFiles = fileDataRepository.findAll();
		
		if(null == allFiles) {
			throw new FileDataException("Files not found");
		}
		
		List<FileDataResponseModel> allFilesResponse = new ArrayList<>();
		
		for(FileDataEntity fde:allFiles) {
			allFilesResponse.add(modelMapper.map(fde, FileDataResponseModel.class));
		}
		return allFilesResponse;
	}
}
