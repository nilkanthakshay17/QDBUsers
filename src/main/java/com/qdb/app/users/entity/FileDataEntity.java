package com.qdb.app.users.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;



@Entity
@Table(name = "file_data")
public class FileDataEntity {

	@Id
	@GeneratedValue
	private int id;
	
	@Column(name = "fileId")
	private String fileId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "type")
	private String type;
	
	@Lob
	@Column(name="filedata", length = 1000)
	private byte[] fileData;

	@OneToOne
	private PostEntity post;
	
	public FileDataEntity() {
		// TODO Auto-generated constructor stub
	}
	
	public FileDataEntity(String fileId, String name, String type, byte[] fileData, PostEntity post) {
		super();
		this.fileId = fileId;
		this.name = name;
		this.type = type;
		this.fileData = fileData;
		this.post = post;
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

	public byte[] getFileData() {
		return fileData;
	}

	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}

	public PostEntity getPost() {
		return post;
	}

	public void setPost(PostEntity post) {
		this.post = post;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
