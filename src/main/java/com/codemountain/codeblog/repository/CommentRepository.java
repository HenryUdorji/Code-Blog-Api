package com.codemountain.codeblog.repository;

import com.codemountain.codeblog.entity.Comments;
import com.codemountain.codeblog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comments, Long> {
}
