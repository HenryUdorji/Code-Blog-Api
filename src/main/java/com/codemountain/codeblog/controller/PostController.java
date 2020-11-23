package com.codemountain.codeblog.controller;


import com.codemountain.codeblog.dto.PostDto;
import com.codemountain.codeblog.entity.Category;
import com.codemountain.codeblog.entity.Post;
import com.codemountain.codeblog.entity.User;
import com.codemountain.codeblog.exception.CodeBlogException;
import com.codemountain.codeblog.repository.CategoryRepository;
import com.codemountain.codeblog.repository.PostRepository;
import com.codemountain.codeblog.service.AuthService;
import com.codemountain.codeblog.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;
    private final CategoryRepository categoryRepository;
    private final AuthService authService;
    private final PostRepository postRepository;


    @PostMapping
    public ResponseEntity createPost(@RequestBody PostDto postDto) {
        Category categoryName = categoryRepository.findByName(postDto.getCategoryName());
        if (categoryName == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Category name - " + postDto.getCategoryName() + " does not exist");
        }
        else {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Category created with Id - " + postService.createPost(postDto).getPostId());
        }
    }


    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPost() {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPost());
    }


    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getSinglePost(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getSinglePost(id));
    }


    @GetMapping("/by-category/{id}")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostByCategory(id));
    }


    @GetMapping("/by-user/{name}")
    public ResponseEntity<List<PostDto>> getPostByUsername(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostByUsername(name));
    }


    @PatchMapping("/update")
    public ResponseEntity updatePost(@RequestBody PostDto postDto) {
        Post existingPost = postRepository.findById(postDto.getPostId())
                .orElseThrow(()-> new CodeBlogException("Post not found"));
        User currentUser = authService.getCurrentUser();

        if (existingPost.getUser() == currentUser) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Post with Id " + postService.updatePost(postDto).getPostId() + " updated");
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You cannot edit another User's post");
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(()-> new CodeBlogException("Post not found"));
        User currentUser = authService.getCurrentUser();

        if (existingPost.getUser() == currentUser) {
            postService.deletePost(id);
            return new ResponseEntity<>("Post with Id " + id + " deleted.", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("You cannot delete another User's post", HttpStatus.FORBIDDEN);
        }
    }

}
