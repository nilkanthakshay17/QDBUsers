package com.qdb.app.users.comm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.qdb.app.users.model.CommentModel;
import com.qdb.app.users.model.PostModel;

@Service
public class PostsAndCommentsService {

	@Autowired
	private PostsAndCommentsServiceFeignClient postsAndCommentsServiceFeignClient;
	
	public ResponseEntity<?> getAllPosts() {
		return postsAndCommentsServiceFeignClient.getAllPosts();
	}

	public ResponseEntity<?> getPostByPostId(int postId) {
		return postsAndCommentsServiceFeignClient.getPostByPostId(postId);
	}

	public ResponseEntity<?> createPost(PostModel postModel) {
		return postsAndCommentsServiceFeignClient.createPost(postModel);
	}
	
	public ResponseEntity<?> getAllComments() {
		return postsAndCommentsServiceFeignClient.getAllComments();
	}

	public ResponseEntity<?> getCommentByCommentId(int commentId) {
		return postsAndCommentsServiceFeignClient.getCommentByCommentId(commentId);
	}

	public ResponseEntity<?> createComment(CommentModel commentModel) {
		return postsAndCommentsServiceFeignClient.createComment(commentModel);
	}
}
