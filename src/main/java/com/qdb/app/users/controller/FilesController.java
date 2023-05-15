package com.qdb.app.users.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.qdb.app.users.entity.FileDataEntity;
import com.qdb.app.users.model.FileDataResponseModel;
import com.qdb.app.users.service.FileDataServiceInt;

@RestController
@RequestMapping("/api/v1.0")
public class FilesController {

	@Autowired
	private FileDataServiceInt fileDataServiceInt;
	
	
	private ModelMapper modelMapper;
	
	public FilesController() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}
	
	@PostMapping("/files/{userId}")
	public ResponseEntity<?> uploadFile(@PathVariable(name = "userId")String userId, @RequestParam(name = "file")MultipartFile file) throws Exception{
		String uploadResult = fileDataServiceInt.uploadFile(userId,file);
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(uploadResult);
	}
	
	@GetMapping("/files/{fileId}")
	public ResponseEntity<?> downloadFile(@PathVariable(name = "fileId")String fileId) throws Exception{
		byte[] downloadResult = fileDataServiceInt.downloadFile(fileId);
		
		return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.valueOf("application/pdf"))
				.body(downloadResult);
	}
	
	@DeleteMapping("/files/{fileId}")
	public ResponseEntity<?> deleteFile(@PathVariable(name = "fileId")String fileId) throws Exception{
		FileDataEntity deletedFile = fileDataServiceInt.deleteFileByFileId(fileId);
		
		FileDataResponseModel deletedFileResponse = modelMapper.map(deletedFile, FileDataResponseModel.class);
		return ResponseEntity.status(HttpStatus.OK)
				.body(deletedFileResponse);
	}
	
	@GetMapping("/files")
	public ResponseEntity<?> getAllFiles() throws Exception{
		List<FileDataEntity> allFiles = fileDataServiceInt.getAllFiles();
		List<FileDataResponseModel> allFilesResponse = new ArrayList<>();
		
		for(FileDataEntity fe:allFiles) {
			allFilesResponse.add(modelMapper.map(fe, FileDataResponseModel.class));
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(allFilesResponse);
	}
}
