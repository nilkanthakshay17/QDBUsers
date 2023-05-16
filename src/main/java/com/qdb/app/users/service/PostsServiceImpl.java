package com.qdb.app.users.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qdb.app.users.entity.FileDataEntity;
import com.qdb.app.users.entity.PostEntity;
import com.qdb.app.users.exception.PostException;
import com.qdb.app.users.model.PostRequestModel;
import com.qdb.app.users.model.PostResponseModel;
import com.qdb.app.users.repository.FileDataRepository;
import com.qdb.app.users.repository.PostsRepository;

@Service
public class PostsServiceImpl implements PostsServiceInt {

	@Autowired
	private PostsRepository postsRepository;
	
	@Autowired
	private FileDataRepository fileDataRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public PostsServiceImpl() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}
	
	@Override
	public List<PostResponseModel> getAllPosts() {
		List<PostEntity> allPosts = postsRepository.findAll();
		
		if(null == allPosts) {
			throw new PostException("Posts not found");
		}
		
		List<PostResponseModel> allPostsResponse = new ArrayList<>();
		for(PostEntity pe: allPosts){
			allPostsResponse.add(modelMapper.map(pe, PostResponseModel.class));
		}
		
		return allPostsResponse;
	}

	@Override
	public PostResponseModel getPostByPostId(String postId) {
		PostEntity post = postsRepository.findByPostId(postId);
		
		if(null == post) {
			throw new PostException("Post not found");
		}
		
		PostResponseModel postResponse = modelMapper.map(post, PostResponseModel.class);
		
		return postResponse;
	}

	@Override
	public PostResponseModel createPost(PostRequestModel createPost, String userId, String fileId) {
		
		PostEntity postToCreate = new PostEntity();
		
		postToCreate.setPostId(UUID.randomUUID().toString());
		postToCreate.setTitle(createPost.getTitle());
		postToCreate.setBody(createPost.getBody());
		
		postToCreate.setUserId(userId);
		
		Optional<FileDataEntity> fileData = fileDataRepository.findByFileId(fileId);
		
		
		FileDataEntity fileDataEntity = fileData.get();
		fileDataEntity.setPost(postToCreate);
		fileDataRepository.save(fileDataEntity);
		
		PostResponseModel createdPostResponse = modelMapper.map(postToCreate, PostResponseModel.class);
		return createdPostResponse;
	}

	@Override
	public PostResponseModel updatePost(PostRequestModel updatePost, String postId) {
		PostEntity postToUpdate = postsRepository.findByPostId(postId);
		
		if(null == postToUpdate) {
			throw new PostException("Post not found");
		}
		
		postToUpdate.setTitle(updatePost.getTitle());
		postToUpdate.setBody(updatePost.getBody());
		
		PostEntity updatedPost = postsRepository.save(postToUpdate);
		
		if(null == updatedPost) {
			throw new PostException("Failed to update post");
		}
		
		PostResponseModel updatedPostResponse = modelMapper.map(updatedPost, PostResponseModel.class);
		
		return updatedPostResponse;
	}

	@Override
	public PostResponseModel deletePostByPostId(String postId) {
		PostEntity postToDelete = postsRepository.findByPostId(postId);
		
		if(null == postToDelete) {
			throw new PostException("Post not found");
		}
		postsRepository.delete(postToDelete);
		PostResponseModel deletedPostResponse = modelMapper.map(postToDelete, PostResponseModel.class);
		return deletedPostResponse;
	}

	@Override
	public String deleteAllPosts() {
		postsRepository.deleteAll();
		return "Deleted All Posts";
	}



}
