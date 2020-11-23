package com.codemountain.codeblog.repository;

import com.codemountain.codeblog.entity.Category;
import com.codemountain.codeblog.entity.Post;
import com.codemountain.codeblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByCategory(Category category);

    List<Post> findAllByUser(User user);

    //List<Post> findAllByPost(Post post);
}
