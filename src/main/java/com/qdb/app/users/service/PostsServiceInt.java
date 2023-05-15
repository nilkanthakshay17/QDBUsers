package com.qdb.app.users.service;

import java.util.List;

import org.springframework.stereotype.Service;


import com.qdb.app.users.model.PostRequestModel;
import com.qdb.app.users.model.PostResponseModel;

@Service
public interface PostsServiceInt {

	public List<PostResponseModel> getAllPosts();
	public PostResponseModel getPostByPostId(String postId);
	public PostResponseModel createPost(PostRequestModel createPost,String userId, String fileId);
	public PostResponseModel updatePost(PostRequestModel updatePost, String postId);
	public PostResponseModel deletePostByPostId(String postId);
	public String deleteAllPosts();
}
