/*
package com.codemountain.codeblog.controller;


import com.codemountain.codeblog.dto.ReactionDto;
import com.codemountain.codeblog.service.ReactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reaction")
@AllArgsConstructor
public class ReactionController {

    private final ReactionService reactionService;

    @PostMapping
    public ResponseEntity react(@RequestBody ReactionDto reactionDto) {
        reactionService.react(reactionDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
*/
