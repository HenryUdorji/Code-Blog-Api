package com.codemountain.codeblog.controller;


import com.codemountain.codeblog.dto.UnlikePostDto;
import com.codemountain.codeblog.service.UnlikePostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/unlikepost")
@AllArgsConstructor
public class UnlikePostController {


    private final UnlikePostService unlikePostService;


    @PostMapping
    public ResponseEntity likePost(@RequestBody UnlikePostDto unlikePostDto) {
        unlikePostService.unlikePost(unlikePostDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    //TODO -> create controller to get all users that un-liked post
}
