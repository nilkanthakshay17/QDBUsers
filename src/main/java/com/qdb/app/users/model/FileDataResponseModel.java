package com.qdb.app.users.model;

public class FileDataResponseModel {

	private String fileId;

	private String name;

	private String type;

	public FileDataResponseModel() {
		// TODO Auto-generated constructor stub
	}
	
	public FileDataResponseModel(String fileId, String name, String type) {
		super();
		this.fileId = fileId;
		this.name = name;
		this.type = type;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
