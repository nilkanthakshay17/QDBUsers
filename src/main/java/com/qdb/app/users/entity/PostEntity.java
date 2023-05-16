package com.qdb.app.users.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "posts_data")
public class PostEntity {

	@Id
	@GeneratedValue
	private int id;
	
	private String postId;
	
	private String fileId;
	
	private String userId;
	
	private String title;
	
	private String body;

	@JsonIgnore
	@OneToMany(mappedBy = "post")
	private List<CommentEntity> comments = new ArrayList<>();;
	
	@OneToOne
	@JoinColumn(name = "fk_file_id")
	private FileDataEntity file;
	
	public PostEntity() {
		
	}

	public PostEntity( String postId,String fileId, String userId, String title, String body) {
		super();
		this.postId = postId;
		this.fileId = fileId;
		this.userId = userId;
		this.title = title;
		this.body = body;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public List<CommentEntity> getComments() {
		return comments;
	}

	public void setComments(List<CommentEntity> comments) {
		this.comments = comments;
	}

	public String getFileId() {
		return fileId;
	}
	
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	
	public void addComment(CommentEntity comment) {
		this.comments.add(comment);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
