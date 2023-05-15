package com.qdb.app.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qdb.app.users.entity.CommentEntity;

@Repository
public interface CommentsRepository extends JpaRepository<CommentEntity, Integer>{

	public CommentEntity findByCommentId(String commentId);
}
