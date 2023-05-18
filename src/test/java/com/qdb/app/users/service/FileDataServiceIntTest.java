package com.qdb.app.users.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.qdb.app.users.model.FileDataResponseModel;

@SpringBootTest
class FileDataServiceIntTest {

	@MockBean
	private FileDataServiceInt fileDataServiceInt;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	public void testUploadFile() throws Exception {

	    byte[] inputArray = "Test String".getBytes();
	    MockMultipartFile mockMultipartFile = new MockMultipartFile("tempFileName",inputArray);
	    
		FileDataResponseModel fileDataResponse = new FileDataResponseModel("F999", "Sample File", "pdf");
		
		when(fileDataServiceInt.uploadFile(eq("U999"), eq(mockMultipartFile))).thenReturn(fileDataResponse);
		
		FileDataResponseModel receivedResponse = fileDataServiceInt.uploadFile("U999", mockMultipartFile);
		
		assertNotNull(receivedResponse);
		assertEquals("F999", receivedResponse.getFileId());
		assertEquals("Sample File", receivedResponse.getName());
		assertEquals("pdf", receivedResponse.getType());
	}
	
	@Test
	public void testDownloadFile() throws Exception {
		byte[] fileInfo = "fileInfo".getBytes();
		
		when(fileDataServiceInt.downloadFile(eq("F999"))).thenReturn(fileInfo);
		
		byte[] receivedResponse = fileDataServiceInt.downloadFile("F999");
		
		assertNotNull(receivedResponse);
		String response = new String(receivedResponse);
		assertEquals("fileInfo", response);
	}
	
	@Test
	public void testDeleteFileByFileId() throws Exception {
		FileDataResponseModel fileDataResponse = new FileDataResponseModel("F999", "Sample File", "pdf");
		
		when(fileDataServiceInt.deleteFileByFileId("F999")).thenReturn(fileDataResponse);
		
		FileDataResponseModel receivedResponse = fileDataServiceInt.deleteFileByFileId("F999");
		
		assertNotNull(receivedResponse);
		assertEquals("F999", receivedResponse.getFileId());
		assertEquals("Sample File", receivedResponse.getName());
		assertEquals("pdf", receivedResponse.getType());
	}
	
	@Test
	public void testGetAllFiles() throws Exception {
		FileDataResponseModel fileDataResponse1 = new FileDataResponseModel("F998", "Sample File", "pdf");
		FileDataResponseModel fileDataResponse2 = new FileDataResponseModel("F999", "Sample File1", "pdf");
		
		List<FileDataResponseModel> fileDataResponses = new ArrayList<>();
		fileDataResponses.add(fileDataResponse1);
		fileDataResponses.add(fileDataResponse2);
		
		when(fileDataServiceInt.getAllFiles()).thenReturn(fileDataResponses);
		
		List<FileDataResponseModel> receivedResponses = fileDataServiceInt.getAllFiles();
		
		assertNotNull(receivedResponses);
		assertEquals(2, fileDataResponses.size());
	}
	
	@Test
	public void testUpdateFileByFileId() throws Exception{
		byte[] inputArray = "Test String".getBytes();
	    MockMultipartFile mockMultipartFile = new MockMultipartFile("tempFileName",inputArray);
	    
		FileDataResponseModel fileDataResponse = new FileDataResponseModel("F999", "Sample File", "pdf");
		
		when(fileDataServiceInt.updateFileByFileId(eq(mockMultipartFile), eq("F999"))).thenReturn(fileDataResponse);
		
		FileDataResponseModel receivedResponse = fileDataServiceInt.updateFileByFileId(mockMultipartFile,"F999");
		
		assertNotNull(receivedResponse);
		assertEquals("F999", receivedResponse.getFileId());
		assertEquals("Sample File", receivedResponse.getName());
		assertEquals("pdf", receivedResponse.getType());
	}

}
