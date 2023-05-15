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
		List<PostResponseModel> allPostsResponse = new ArrayList<>();
		for(PostEntity pe: allPosts){
			allPostsResponse.add(modelMapper.map(pe, PostResponseModel.class));
		}
		
		return allPostsResponse;
	}

	@Override
	public PostResponseModel getPostByPostId(String postId) {
		PostEntity post = postsRepository.findByPostId(postId);
		
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
		
		if(null != fileData) {
			postToCreate.setFileId(fileData.get().getFileId());
			fileData.get().setPost(postToCreate);
		}
		
		PostEntity createdPost = postsRepository.save(postToCreate);
		FileDataEntity resavedFIleData = fileDataRepository.save(fileData.get());
		
		PostResponseModel createdPostResponse = modelMapper.map(createdPost, PostResponseModel.class);
		return createdPostResponse;
	}

	@Override
	public PostResponseModel updatePost(PostRequestModel updatePost, String postId) {
		PostEntity postToUpdate = postsRepository.findByPostId(postId);
		
		postToUpdate.setTitle(updatePost.getTitle());
		postToUpdate.setBody(updatePost.getBody());
		
		PostEntity updatedPost = postsRepository.save(postToUpdate);
		
		PostResponseModel updatedPostResponse = modelMapper.map(updatedPost, PostResponseModel.class);
		
		return updatedPostResponse;
	}

	@Override
	public PostResponseModel deletePostByPostId(String postId) {
		PostEntity postToDelete = postsRepository.findByPostId(postId);
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
