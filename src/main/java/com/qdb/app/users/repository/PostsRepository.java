package com.qdb.app.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qdb.app.users.entity.PostEntity;

@Repository
public interface PostsRepository extends JpaRepository<PostEntity, Integer>{

	public PostEntity findByPostId(String postId);

}
