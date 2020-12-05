package com.codemountain.codeblog.controller;


import com.codemountain.codeblog.dto.PostDto;
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


    @PostMapping
    public ResponseEntity createPost(@RequestBody PostDto postDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Category created with Id - " + postService.createPost(postDto).getPostId());
    }


    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPost() {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPost());
    }

    @GetMapping("/draft")
    public ResponseEntity<List<PostDto>> getAllDraftPost() {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllDraftPost());
    }


    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getSinglePost(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getSinglePost(id));
    }


    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostByCategory(id));
    }


    @GetMapping("/user/{name}")
    public ResponseEntity<List<PostDto>> getPostByUsername(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostByUsername(name));
    }


    @PatchMapping("/update")
    public ResponseEntity updatePost(@RequestBody PostDto postDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body("Post with Id " + postService.updatePost(postDto).getPostId() + " updated");
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity deletePost(@PathVariable long id) {
        postService.deletePost(id);
        return ResponseEntity.status(HttpStatus.OK).body("Post with Id - " + id + " deleted.");
    }

}
