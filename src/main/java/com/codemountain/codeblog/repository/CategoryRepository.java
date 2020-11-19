package com.codemountain.codeblog.repository;

import com.codemountain.codeblog.entity.Category;
import com.codemountain.codeblog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
