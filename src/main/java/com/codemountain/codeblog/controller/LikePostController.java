package com.codemountain.codeblog.controller;


import com.codemountain.codeblog.dto.LikePostDto;
import com.codemountain.codeblog.service.LikePostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/likepost")
@AllArgsConstructor
public class LikePostController {

    private final LikePostService likePostService;


    @PostMapping
    public ResponseEntity likePost(@RequestBody LikePostDto likePostDto) {
        likePostService.likePost(likePostDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    //TODO -> create controller to get all users that liked post
}
