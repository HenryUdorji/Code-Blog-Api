package com.codemountain.codeblog.controller;

import com.codemountain.codeblog.dto.CommentsDto;
import com.codemountain.codeblog.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;


    @PostMapping
    public ResponseEntity createComment(@RequestBody CommentsDto commentsDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Comment created with Id - " + commentService.createComment(commentsDto).getCommentId());
    }


    @GetMapping("/post/{id}")
    public ResponseEntity<List<CommentsDto>> getCommentsByPost(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentsByPost(id));
    }


    @GetMapping("/user/{username}")
    public ResponseEntity<List<CommentsDto>> getCommentsByUser(@PathVariable String username) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentsByUser(username));
    }


    @PatchMapping("/update")
    public ResponseEntity updateComment(@RequestBody CommentsDto commentsDto) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Comment with Id " + commentService.updateComment(commentsDto).getCommentId() + " updated");
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteComment(@PathVariable long id) {
            commentService.deleteComment(id);
            return ResponseEntity.status(HttpStatus.OK).body("Comment with Id " + id + " deleted.");
    }
}
