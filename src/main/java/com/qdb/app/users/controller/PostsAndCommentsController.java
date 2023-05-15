package com.qdb.app.users.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qdb.app.users.comm.PostsAndCommentsService;
import com.qdb.app.users.model.CommentModel;
import com.qdb.app.users.model.CommentRequestModel;
import com.qdb.app.users.model.PostModel;
import com.qdb.app.users.model.PostRequestModel;
import com.qdb.app.users.service.CommentsServiceInt;
import com.qdb.app.users.service.PostsServiceInt;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/api/v1.0")
public class PostsAndCommentsController {

	@Autowired
	private PostsAndCommentsService postsAndCommentsService;

	@Autowired
	private PostsServiceInt postsServiceInt;
	
	@Autowired
	private CommentsServiceInt commentsServiceInt;
	
	public static final String POST_AND_COMMENTS_SERVICE = "postsAndCommentsService";

	PostModel postModel;
	List<PostModel> postModels;
	
	CommentModel commentModel;
	List<CommentModel> commentModels;
	
	public PostsAndCommentsController() {
		postModel = new PostModel(1,1,"Dummy","Dummy");
		postModels = new ArrayList<>();
		postModels.add(postModel);
		
		commentModel = new CommentModel(1,1,"Dummy","abc@gmail.com","Dummy");
		commentModels = new ArrayList<>();
		commentModels.add(commentModel);
	}
	
	@GetMapping("/posts")
	@CircuitBreaker(name = POST_AND_COMMENTS_SERVICE, fallbackMethod = "getAllPostsFallback")
	public ResponseEntity<?> getAllPosts() {
		return postsAndCommentsService.getAllPosts();
	}

	// fallback method
	public ResponseEntity<?> getAllPostsFallback(Exception e) {
		return ResponseEntity.status(HttpStatus.OK).body(postModels);
	}

	@GetMapping("/posts/{postId}")
	@CircuitBreaker(name = POST_AND_COMMENTS_SERVICE, fallbackMethod = "getPostByPostIdFallback")
	public ResponseEntity<?> getPostByPostId(@PathVariable(name = "postId") int postId) {
		return postsAndCommentsService.getPostByPostId(postId);
	}

	// fallback method
	public ResponseEntity<?> getPostByPostIdFallback(Exception e) {
		return ResponseEntity.status(HttpStatus.OK).body(postModel);
	}

	@PostMapping("/posts")
	@CircuitBreaker(name = POST_AND_COMMENTS_SERVICE, fallbackMethod = "createPostFallback")
	public ResponseEntity<?> createPost(@RequestBody PostModel postModel) {
		return postsAndCommentsService.createPost(postModel);
	}

	// fallback method
	public ResponseEntity<?> createPostFallback(Exception e) {
		return ResponseEntity.status(HttpStatus.OK).body(postModel);
	}

	@GetMapping("/comments")
	@CircuitBreaker(name = POST_AND_COMMENTS_SERVICE, fallbackMethod = "getAllCommentsFallback")
	public ResponseEntity<?> getAllComments() {
		return postsAndCommentsService.getAllComments();
	}

	// fallback method
	public ResponseEntity<?> getAllCommentsFallback(Exception e) {
		return ResponseEntity.status(HttpStatus.OK).body(commentModels);
	}

	@GetMapping("/comments/{commentId}")
	@CircuitBreaker(name = POST_AND_COMMENTS_SERVICE, fallbackMethod = "getCommentByCommentIdFallback")
	public ResponseEntity<?> getCommentByCommentId(@PathVariable(name = "commentId") int commentId) {
		return postsAndCommentsService.getCommentByCommentId(commentId);
	}

	// fallback method
	public ResponseEntity<?> getCommentByCommentIdFallback(Exception e) {
		return ResponseEntity.status(HttpStatus.OK).body(commentModel);
	}

	@PostMapping("/comments")
	@CircuitBreaker(name = POST_AND_COMMENTS_SERVICE, fallbackMethod = "createCommentFallback")
	public ResponseEntity<?> createComment(@RequestBody CommentModel commentModel) {
		return postsAndCommentsService.createComment(commentModel);
	}

	// fallback method
	public ResponseEntity<?> createCommentFallback(Exception e) {
		return ResponseEntity.status(HttpStatus.OK).body(commentModel);
	}
	
	
	
	//POSTs APIs using local repo of 
	
	
	@GetMapping("/posts/local")
	@CircuitBreaker(name = POST_AND_COMMENTS_SERVICE, fallbackMethod = "getAllPostsFallback")
	public ResponseEntity<?> getAllPostsLocal() {
		return ResponseEntity.status(HttpStatus.OK).body(postsServiceInt.getAllPosts());
	}

	@GetMapping("/posts/local/{postId}")
	public ResponseEntity<?> getPostByPostIdLocal(@PathVariable(name = "postId") String postId) {
		return ResponseEntity.status(HttpStatus.OK).body(postsServiceInt.getPostByPostId(postId));
	}

	@PostMapping("/posts/local/{userId}/{fileId}")
	public ResponseEntity<?> createPostLocal(@RequestBody PostRequestModel postModel,@PathVariable(name = "userId")String userId,@PathVariable(name = "fileId")String fileId) {
		return ResponseEntity.status(HttpStatus.OK).body(postsServiceInt.createPost(postModel,userId,fileId));
	}

	@PutMapping("/posts/local/{postId}")
	public ResponseEntity<?> updatePostLocal(@RequestBody PostRequestModel updateModel,@PathVariable(name = "postId")String postId) {
		return ResponseEntity.status(HttpStatus.OK).body(postsServiceInt.updatePost(updateModel,postId));
	}

	@DeleteMapping("/posts/local/{postId}")
	public ResponseEntity<?> deletedPostLocal(@PathVariable(name = "postId")String postId){
		return ResponseEntity.status(HttpStatus.OK).body(postsServiceInt.deletePostByPostId(postId));
	}
	
	@DeleteMapping("/posts/local")
	public ResponseEntity<?> deletedAllPostsLocal(){
		return ResponseEntity.status(HttpStatus.OK).body(postsServiceInt.deleteAllPosts());
	}
	
	//COMMENTs APIs using local repo of 
	
	@GetMapping("/comments/local")
	public ResponseEntity<?> getAllCommentsLocal() {
		return ResponseEntity.status(HttpStatus.OK).body(commentsServiceInt.getAllComments());
	}

	@GetMapping("/comments/local/{commentId}")
	public ResponseEntity<?> getCommentByCommentIdLocal(@PathVariable(name = "commentId") String commentId) {
		return ResponseEntity.status(HttpStatus.OK).body(commentsServiceInt.getCommentByCommentId(commentId));
	}

	@PostMapping("/comments/local/{userId}/{postId}")
	public ResponseEntity<?> createCommentLocal(@RequestBody CommentRequestModel commentModel,@PathVariable(name = "userId")String userId,@PathVariable(name = "postId")String postId) {
		return ResponseEntity.status(HttpStatus.OK).body(commentsServiceInt.createComment(commentModel, userId, postId));
	}

	@PutMapping("/comments/local/{commentId}")
	public ResponseEntity<?> updateCommentLocal(@RequestBody CommentRequestModel commentModel,@PathVariable(name = "commentId")String commentId) {
		return ResponseEntity.status(HttpStatus.OK).body(commentsServiceInt.updateComment(commentModel,commentId));
	}

	@DeleteMapping("/comments/local/{commentId}")
	public ResponseEntity<?> deleteCommentLocal(@PathVariable(name = "commentId") String commentId) {
		return ResponseEntity.status(HttpStatus.OK).body(commentsServiceInt.deleteCommentByCommentId(commentId));
	}
	
	@DeleteMapping("/comments/local")
	public ResponseEntity<?> deleteAllCommentsLocal() {
		return ResponseEntity.status(HttpStatus.OK).body(commentsServiceInt.deleteAllComments());
	}
	
}
