package com.codemountain.codeblog.repository;

import com.codemountain.codeblog.entity.Post;
import com.codemountain.codeblog.entity.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {
}
