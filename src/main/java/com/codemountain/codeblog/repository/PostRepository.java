package com.codemountain.codeblog.repository;

import com.codemountain.codeblog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
