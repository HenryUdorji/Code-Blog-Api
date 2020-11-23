package com.codemountain.codeblog.repository;

import com.codemountain.codeblog.entity.LikePost;
import com.codemountain.codeblog.entity.Post;
import com.codemountain.codeblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {

    List<LikePost> findAllByPost(Post post);

    Optional<LikePost> findByPostAndUser(Post post, User currentUser);

    LikePost deleteByPost(Post post);
}
