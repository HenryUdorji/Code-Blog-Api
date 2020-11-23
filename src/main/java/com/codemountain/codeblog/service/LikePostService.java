package com.codemountain.codeblog.service;

import com.codemountain.codeblog.dto.LikePostDto;
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
public class LikePostService {

    private final LikePostRepository likePostRepository;
    private final PostRepository postRepository;
    private final AuthService authService;
    private final UnlikePostRepository unlikePostRepository;

    @Transactional
    public void likePost(LikePostDto likePostDto) {
        Post post = postRepository.findById(likePostDto.getPostId())
                .orElseThrow(() -> new CodeBlogException("Post not found"));
        Optional<LikePost> byPostAndUser = likePostRepository.findByPostAndUser(post, authService.getCurrentUser());

        if (byPostAndUser.isPresent() && byPostAndUser.get().getPost().getPostId()
                .equals(likePostDto.getPostId())) {
            throw new CodeBlogException("This post has been liked by you already");
        }
        else {
            likePostRepository.save(mapToLikePost(post));

            Optional<UnlikePost> unlikeByPostAndUser = unlikePostRepository.findByPostAndUser(post, authService.getCurrentUser());
            if (unlikeByPostAndUser.isPresent() && unlikeByPostAndUser.get().getPost().getPostId()
                    .equals(likePostDto.getPostId())) {
                unlikePostRepository.deleteById(unlikeByPostAndUser.get().getUnlikeId());
            }
        }
    }

    private LikePost mapToLikePost(Post post) {
        return LikePost.builder()
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}
