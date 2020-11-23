/*
package com.codemountain.codeblog.service;


import com.codemountain.codeblog.dto.ReactionDto;
import com.codemountain.codeblog.entity.Post;
import com.codemountain.codeblog.entity.Reaction;
import com.codemountain.codeblog.entity.ReactionType;
import com.codemountain.codeblog.exception.CodeBlogException;
import com.codemountain.codeblog.repository.PostRepository;
import com.codemountain.codeblog.repository.ReactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ReactionService {

    private final PostRepository postRepository;
    private final ReactionRepository reactionRepository;
    private final AuthService authService;


    */
/**
     * The reaction has only two options: 1. Like, 2. Unlike
     * So i first check if the current user has already Liked / Unlike a post
     * to avoid multiple entries by a single user.
     *//*

    @Transactional
    public void react(ReactionDto reactionDto) {
        Post post = postRepository.findById(reactionDto.getPostId())
                .orElseThrow(() -> new CodeBlogException("Post with Id - " + reactionDto.getPostId() + " not found"));
        Optional<Reaction> reactByPostAndUser = reactionRepository.findTopByPostAndUserOrderByReactionIdDesc(post, authService.getCurrentUser());

        if (reactByPostAndUser.isPresent() && reactByPostAndUser.get().getReactionType()
                .equals(reactionDto.getReactionType())) {
            throw new CodeBlogException("You have already " + reactionDto.getReactionType() + " this post");
        }
        if (ReactionType.LIKE.equals(reactionDto.getReactionType())) {
            post.setReaction(post.getReaction() + 1);
        }
        else if (ReactionType.UNLIKE.equals(reactionDto.getReactionType())){
            post.setReaction(post.getReaction() - 1);
        }

        reactionRepository.save(mapToReaction(reactionDto, post));
        postRepository.save(post);
    }


    private Reaction mapToReaction(ReactionDto reactionDto, Post post) {
        return Reaction.builder()
                .reactionType(reactionDto.getReactionType())
                .user(authService.getCurrentUser())
                .post(post)
                .build();
    }
}
*/
