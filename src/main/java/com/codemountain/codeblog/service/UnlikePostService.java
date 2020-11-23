package com.codemountain.codeblog.service;


import com.codemountain.codeblog.dto.UnlikePostDto;
import com.codemountain.codeblog.entity.LikePost;
import com.codemountain.codeblog.entity.Post;
import com.codemountain.codeblog.entity.UnlikePost;
import com.codemountain.codeblog.exception.CodeBlogException;
import com.codemountain.codeblog.repository.LikePostRepository;
import com.codemountain.codeblog.repository.PostRepository;
import com.codemountain.codeblog.repository.UnlikePostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UnlikePostService {

    private final UnlikePostRepository unlikePostRepository;
    private final PostRepository postRepository;
    private final AuthService authService;
    private final LikePostRepository likePostRepository;


    @Transactional
    public void unlikePost(UnlikePostDto unlikePostDto) {
        Post post = postRepository.findById(unlikePostDto.getPostId())
                .orElseThrow(() -> new CodeBlogException("Post not found"));
        Optional<UnlikePost> byPostAndUser = unlikePostRepository.findByPostAndUser(post, authService.getCurrentUser());

        if (byPostAndUser.isPresent() && byPostAndUser.get().getPost().getPostId()
                .equals(unlikePostDto.getPostId())) {
            throw new CodeBlogException("This post has been un-liked by you already");
        }
        else {
            unlikePostRepository.save(mapToUnlikePost(post));

            Optional<LikePost> likeByPostAndUser = likePostRepository.findByPostAndUser(post, authService.getCurrentUser());
            if (likeByPostAndUser.isPresent() && likeByPostAndUser.get().getPost().getPostId()
                    .equals(unlikePostDto.getPostId())) {
                likePostRepository.deleteById(likeByPostAndUser.get().getLikeId());
            }
        }
    }

    private UnlikePost mapToUnlikePost(Post post) {
        return UnlikePost.builder()
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}
