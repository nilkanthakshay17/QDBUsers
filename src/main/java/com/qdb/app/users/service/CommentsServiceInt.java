package com.qdb.app.users.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.qdb.app.users.model.CommentRequestModel;
import com.qdb.app.users.model.CommentResponseModel;

@Service
public interface CommentsServiceInt {

	public List<CommentResponseModel> getAllComments();
	public CommentResponseModel getCommentByCommentId(String commentId);
	public CommentResponseModel createComment(CommentRequestModel createComment,String userId,String postId);
	public CommentResponseModel updateComment(CommentRequestModel updateComment, String commentId);
	public CommentResponseModel deleteCommentByCommentId(String commentId);
	public String deleteAllComments();
}
