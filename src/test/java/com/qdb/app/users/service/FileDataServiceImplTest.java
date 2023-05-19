package com.qdb.app.users.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.qdb.app.users.entity.FileDataEntity;
import com.qdb.app.users.entity.UserEntity;
import com.qdb.app.users.model.FileDataResponseModel;
import com.qdb.app.users.repository.FileDataRepository;
import com.qdb.app.users.repository.UsersRepository;

@SpringBootTest
class FileDataServiceImplTest {

	@Autowired
	private FileDataServiceImpl fileDataServiceImpl;
	
	@MockBean
	private FileDataRepository fileDataRepository;
	
	@MockBean
	private UsersRepository usersRepository;
	
	@MockBean
	ModelMapper modelMapper;
	
	@MockBean
	MultipartFile multipartFile;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	public void testUploadFile() throws Exception {

	    byte[] inputArray = "fileInfo".getBytes();
	    
	    
		FileDataResponseModel fileDataResponse = new FileDataResponseModel("F999", "Sample File", "pdf");
		
		UserEntity userEntity = new UserEntity(1, "U999", "A999", "A@gmail.com", "A@123");
		
		when(multipartFile.getOriginalFilename()).thenReturn("sample");
		when(multipartFile.getBytes()).thenReturn(inputArray);
		when(multipartFile.getContentType()).thenReturn("application/pdf");
		when(usersRepository.findByUserId(eq("U999"))).thenReturn(Optional.of(userEntity));
		when(usersRepository.save(userEntity)).thenReturn(userEntity);
		when(modelMapper.map(any(), eq(FileDataResponseModel.class))).thenReturn(fileDataResponse);
		
		
		FileDataResponseModel receivedResponse = fileDataServiceImpl.uploadFile("U999",multipartFile);
	
		assertNotNull(receivedResponse);
	}
	
	@Test
	public void testDownloadFile() throws Exception {
		byte[] fileInfo = "fileInfo".getBytes();
		
		FileDataEntity fileEntity = new FileDataEntity("F999", "File1", "pdf", fileInfo, null);
		
		when(multipartFile.getOriginalFilename()).thenReturn("sample");
		when(multipartFile.getBytes()).thenReturn(fileInfo);
		when(multipartFile.getContentType()).thenReturn("application/pdf");
		when(fileDataRepository.findByFileId(eq("F999"))).thenReturn(Optional.of(fileEntity));
		
		byte[] receivedResponse = fileDataServiceImpl.downloadFile("F999");
		
		assertNotNull(receivedResponse);
	}
	
	@Test
	public void testDeleteFileByFileId() throws Exception {
		FileDataResponseModel fileDataResponse = new FileDataResponseModel("F999", "Sample File", "pdf");
		
		FileDataEntity fileEntity = new FileDataEntity("F999", "File1", "pdf", null, null);
		
		UserEntity userEntity = new UserEntity(1, "U999", "A999", "A@gmail.com", "A@123");
		fileEntity.setQdbuser(userEntity);
		
		when(fileDataRepository.findByFileId(eq("F999"))).thenReturn(Optional.of(fileEntity));
		when(usersRepository.findByUserId(eq("U999"))).thenReturn(Optional.of(userEntity));
		when(usersRepository.save(userEntity)).thenReturn(userEntity);
		when(modelMapper.map(any(), eq(FileDataResponseModel.class))).thenReturn(fileDataResponse);
		
		FileDataResponseModel receivedResponse = fileDataServiceImpl.deleteFileByFileId("F999");
		
		assertNotNull(receivedResponse);	
		
	}
	
	@Test
	public void testGetAllFiles() throws Exception {
		FileDataEntity fileEntity1 = new FileDataEntity("F999", "File1", "pdf", null, null);
		FileDataEntity fileEntity2 = new FileDataEntity("F999", "File1", "pdf", null, null);
		List<FileDataEntity> fileEntities = new ArrayList<>();
		fileEntities.add(fileEntity1);
		fileEntities.add(fileEntity2);
		
		FileDataResponseModel fileDataResponse = new FileDataResponseModel("F998", "Sample File", "pdf");
		
		List<FileDataResponseModel> fileDataResponses = new ArrayList<>();
		fileDataResponses.add(fileDataResponse);
		fileDataResponses.add(fileDataResponse);
		
		when(fileDataRepository.findAll()).thenReturn(fileEntities);
		when(modelMapper.map(any(), eq(FileDataResponseModel.class))).thenReturn(fileDataResponse);
		
		List<FileDataResponseModel> receivedResponses = fileDataServiceImpl.getAllFiles();
		
		assertNotNull(receivedResponses);
		assertEquals(2, fileDataResponses.size());
		
	}
	
	@Test
	public void testUpdateFileByFileId() throws Exception{
		byte[] inputArr = "Test String".getBytes();
		byte[] fileInfo = "fileInfo".getBytes();
		
		FileDataEntity fileEntity = new FileDataEntity("F999", "File1", "pdf", inputArr, null);
		FileDataResponseModel fileDataResponse = new FileDataResponseModel("F999", "Sample File", "pdf");
		
	
		when(multipartFile.getOriginalFilename()).thenReturn("sample");
		when(multipartFile.getBytes()).thenReturn(fileInfo);
		when(multipartFile.getContentType()).thenReturn("application/pdf");
		when(fileDataRepository.findByFileId(eq("F999"))).thenReturn(Optional.of(fileEntity));
		when(fileDataRepository.save(fileEntity)).thenReturn(fileEntity);
		when(modelMapper.map(any(), eq(FileDataResponseModel.class))).thenReturn(fileDataResponse);
		
		FileDataResponseModel receivedResponse = fileDataServiceImpl.updateFileByFileId(multipartFile, "F999");
		
		assertNotNull(receivedResponse);
	}
}
