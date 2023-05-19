package com.qdb.app.users.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qdb.app.users.model.FileDataResponseModel;
import com.qdb.app.users.service.FileDataServiceInt;

import feign.Request.HttpMethod;

@WebMvcTest(FilesController.class)
class FilesControllerTest {

	@Autowired
	MockMvc mockMvc;

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
		String uploadFileURL = "/api/v1.0/files/U999";

		byte[] fileInfo = "fileInfo".getBytes();
		MockMultipartFile multipartFile = new MockMultipartFile("file","samplefile","application/pdf", fileInfo);
		
		FileDataResponseModel fileResponse = new FileDataResponseModel("F999", "File1", "pdf");

		
		
		when(fileDataServiceInt.uploadFile(eq("U999"), eq(multipartFile))).thenReturn(fileResponse);
		
	    MockMultipartHttpServletRequestBuilder builder =
	            MockMvcRequestBuilders.multipart(uploadFileURL);
	    builder.with(new RequestPostProcessor() {
			
			@Override
			public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
				request.setMethod("POST");
				return request;
			}
		});

		MvcResult result = mockMvc.perform(builder.file(multipartFile)
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		
				
		assertNotNull(result);
		assertEquals(200, result.getResponse().getStatus());
		
		FileDataResponseModel recevedResponse = mapFromJson(result.getResponse().getContentAsString(), FileDataResponseModel.class);
		
		assertNotNull(recevedResponse);
		assertEquals("F999", recevedResponse.getFileId());
	}

	@Test
	public void testDownloadFile() throws Exception{
		String downloadFileURL = "/api/v1.0/files/F999";
		
		byte[] fileInfo = "fileInfo".getBytes();
		
		when(fileDataServiceInt.downloadFile("F999")).thenReturn(fileInfo);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(downloadFileURL)
				.contentType(MediaType.APPLICATION_PDF))
				.andReturn();
		
		assertNotNull(result);
		assertEquals(200, result.getResponse().getStatus());
		String outputFileInfo = new String(result.getResponse().getContentAsString().getBytes());
		String inputFileInfo = new String(fileInfo);
		
		assertEquals(inputFileInfo, outputFileInfo);
	}

	@Test
	public void testDeleteFile() throws Exception {

		String deleteFileURL = "/api/v1.0/files/F999";

		FileDataResponseModel fileResponse = new FileDataResponseModel("F999", "File1", "pdf");

		when(fileDataServiceInt.deleteFileByFileId(eq("F999"))).thenReturn(fileResponse);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(deleteFileURL)
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		
		assertNotNull(result);
		assertEquals(200, result.getResponse().getStatus());
		FileDataResponseModel receivedResponse = mapFromJson(result.getResponse().getContentAsString(), FileDataResponseModel.class);
		
		assertNotNull(receivedResponse);
		assertEquals("F999", receivedResponse.getFileId());
	}

	@Test
	public void testGetllFiles() throws Exception {
		String getAllFilesURL = "/api/v1.0/files";
		
		FileDataResponseModel fileResponse1 = new FileDataResponseModel("F999", "File1", "pdf");
		FileDataResponseModel fileResponse2 = new FileDataResponseModel("F998", "File2", "pdf");
		
		List<FileDataResponseModel> fileResponses = new ArrayList<>();
		fileResponses.add(fileResponse1);
		fileResponses.add(fileResponse2);
		
		when(fileDataServiceInt.getAllFiles()).thenReturn(fileResponses);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(getAllFilesURL)
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		
		assertNotNull(result);
		assertEquals(200, result.getResponse().getStatus());
		
		List<FileDataResponseModel> receivedFileResponses = mapFromJson(result.getResponse().getContentAsString(), List.class);
		
		assertNotNull(receivedFileResponses);
		assertEquals(2, receivedFileResponses.size());
		
	}

	@Test
	public void testUpdateFile() throws Exception {
		String updateFileURL = "/api/v1.0/files/F999";

		byte[] fileInfo = "fileInfo".getBytes();
		MockMultipartFile multipartFile = new MockMultipartFile("file","samplefile","application/pdf", fileInfo);
		
		FileDataResponseModel fileResponse = new FileDataResponseModel("F999", "File1", "pdf");

		
		
		when(fileDataServiceInt.updateFileByFileId(eq(multipartFile),eq("F999"))).thenReturn(fileResponse);
		
	    MockMultipartHttpServletRequestBuilder builder =
	            MockMvcRequestBuilders.multipart(updateFileURL);
	    builder.with(new RequestPostProcessor() {
			
			@Override
			public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
				request.setMethod("PUT");
				return request;
			}
		});

		MvcResult result = mockMvc.perform(builder.file(multipartFile)
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn();
				
		assertNotNull(result);
		assertEquals(202, result.getResponse().getStatus());
		
		FileDataResponseModel recevedResponse = mapFromJson(result.getResponse().getContentAsString(), FileDataResponseModel.class);
		
		assertNotNull(recevedResponse);
		assertEquals("F999", recevedResponse.getFileId());
		
	}

	protected String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}
}
