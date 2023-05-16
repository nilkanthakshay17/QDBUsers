package com.qdb.app.users.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qdb.app.users.entity.CommentEntity;
import com.qdb.app.users.entity.PostEntity;
import com.qdb.app.users.entity.UserEntity;
import com.qdb.app.users.exception.CommentException;
import com.qdb.app.users.exception.PostException;
import com.qdb.app.users.exception.UserException;
import com.qdb.app.users.model.CommentRequestModel;
import com.qdb.app.users.model.CommentResponseModel;
import com.qdb.app.users.repository.CommentsRepository;
import com.qdb.app.users.repository.PostsRepository;
import com.qdb.app.users.repository.UsersRepository;

@Service
public class CommentsServiceImpl implements CommentsServiceInt {

	@Autowired
	private CommentsRepository commentsRepository;

	@Autowired
	private PostsRepository postsRepository;

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private ModelMapper modelmapper;

	public CommentsServiceImpl() {
		modelmapper = new ModelMapper();
		modelmapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	@Override
	public List<CommentResponseModel> getAllComments() {
		List<CommentEntity> allComments = commentsRepository.findAll();

		if(null == allComments) {
			throw new CommentException("Comments not found");
		}
		
		List<CommentResponseModel> allCommentsResponse = new ArrayList<>();

		for (CommentEntity ce : allComments) {
			allCommentsResponse.add(modelmapper.map(ce, CommentResponseModel.class));
		}

		return allCommentsResponse;
	}

	@Override
	public CommentResponseModel getCommentByCommentId(String commentId) {
		CommentEntity comment = commentsRepository.findByCommentId(commentId);
		
		if(null == comment) {
			throw new CommentException("Comment not found");
		}
		
		CommentResponseModel commentResponse = modelmapper.map(comment, CommentResponseModel.class);
		return commentResponse;
	}

	@Override
	public CommentResponseModel createComment(CommentRequestModel createComment, String userId, String postId) {
		CommentEntity commentEntity = new CommentEntity();
		commentEntity.setCommentId(UUID.randomUUID().toString());

		Optional<UserEntity> commentingUser = usersRepository.findByUserId(userId);
		if (null == commentingUser || commentingUser.equals(Optional.empty())) {
			throw new UserException("User notfound");
		} else {
			commentEntity.setEmail(commentingUser.get().getEmail());
			commentEntity.setName(commentingUser.get().getUserName());
		}

		commentEntity.setPostId(postId);
		commentEntity.setBody(createComment.getBody());

		CommentEntity createdComment = commentsRepository.save(commentEntity);

		PostEntity postEntity = postsRepository.findByPostId(postId);
		
		if(null != postEntity) {
			postEntity.addComment(createdComment);
			postsRepository.save(postEntity);
		}
		else {
			throw new PostException("Post not found");
		}
		
		CommentResponseModel createdCommentResponse = modelmapper.map(createdComment, CommentResponseModel.class);
		
		return createdCommentResponse;
	}

	@Override
	public CommentResponseModel updateComment(CommentRequestModel updateComment, String commentId) {
		CommentEntity comment = commentsRepository.findByCommentId(commentId);
		
		if(null == comment) {
			throw new CommentException("Comment not found");
		}
		comment.setBody(updateComment.getBody());
		CommentEntity updatedComment = commentsRepository.save(comment);
		CommentResponseModel updatedCommentResponse = modelmapper.map(updatedComment, CommentResponseModel.class);
		return updatedCommentResponse;
	}

	@Override
	public CommentResponseModel deleteCommentByCommentId(String commentId) {
		CommentEntity comment = commentsRepository.findByCommentId(commentId);
		
		if(null == comment) {
			throw new CommentException("Comments not found");
		}
		commentsRepository.delete(comment);
		CommentResponseModel deletedComment = modelmapper.map(comment, CommentResponseModel.class);
		return deletedComment;
	}

	@Override
	public String deleteAllComments() {
		commentsRepository.deleteAll();
		return "Deleted All Comments";
	}
}
