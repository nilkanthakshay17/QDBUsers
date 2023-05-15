package com.qdb.app.users.comm;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.qdb.app.users.model.CommentModel;
import com.qdb.app.users.model.PostModel;


@FeignClient(name = "qdbpostsandcomments")
public interface PostsAndCommentsServiceFeignClient {
	
	@GetMapping("/api/v1.0/posts")
	public ResponseEntity<?> getAllPosts();
	
	@GetMapping("/api/v1.0/posts/{postId}")
	public ResponseEntity<?> getPostByPostId(@PathVariable(name = "postId")int postId);
	
	@PostMapping("/api/v1.0/posts")
	public ResponseEntity<?> createPost(@RequestBody PostModel createPost);

	@GetMapping("/api/v1.0/comments")
	public ResponseEntity<?> getAllComments();
	
	@GetMapping("/api/v1.0/comments/{commentId}")
	public ResponseEntity<?> getCommentByCommentId(@PathVariable(name = "commentId")int commentId);
	
	@PostMapping("/api/v1.0/comments")
	public ResponseEntity<?> createComment(@RequestBody CommentModel createComment);
	
}
