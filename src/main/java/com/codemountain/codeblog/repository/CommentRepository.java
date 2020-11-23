package com.codemountain.codeblog.repository;

import com.codemountain.codeblog.entity.Comments;
import com.codemountain.codeblog.entity.Post;
import com.codemountain.codeblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comments, Long> {

    List<Comments> findAllByPost(Post post);

    List<Comments> findAllByUser(User user);
}
