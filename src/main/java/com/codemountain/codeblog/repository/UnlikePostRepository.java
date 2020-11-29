package com.codemountain.codeblog.repository;

import com.codemountain.codeblog.entity.Post;
import com.codemountain.codeblog.entity.UnlikePost;
import com.codemountain.codeblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UnlikePostRepository extends JpaRepository<UnlikePost, Long> {

    List<UnlikePost> findAllByPost(Post post);

    Optional<UnlikePost> findByPostAndUser(Post post, User currentUser);

}
