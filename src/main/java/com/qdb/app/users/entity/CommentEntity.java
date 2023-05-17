package com.qdb.app.users.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="comments_data")
public class CommentEntity {

	@Id
	@GeneratedValue
	private int id;
	
	private String commentId;
	
	private String postId;
	
	private String name;
	
	private String email;
	
	private String body;

	@ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE,CascadeType.REFRESH})
	@JoinColumn(name = "comment_post_id",referencedColumnName = "id")
	private PostEntity post;
	
	public CommentEntity() {
		// TODO Auto-generated constructor stub
	}
	
	public CommentEntity(String commentId, String postId, String name, String email, String body) {
		super();
		this.commentId = commentId;
		this.postId = postId;
		this.name = name;
		this.email = email;
		this.body = body;
	}


	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
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
