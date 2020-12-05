package com.codemountain.codeblog.service;


import com.codemountain.codeblog.dto.PostDto;
import com.codemountain.codeblog.entity.Category;
import com.codemountain.codeblog.entity.Post;
import com.codemountain.codeblog.entity.User;
import com.codemountain.codeblog.exception.CodeBlogException;
import com.codemountain.codeblog.repository.*;
import com.codemountain.codeblog.utils.FormatTime;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentRepository commentRepository;
    private final LikePostRepository likePostRepository;
    private final UnlikePostRepository unlikePostRepository;


    @Transactional
    public Post createPost(PostDto postDto) {
        Category categoryName = categoryRepository.findByName(postDto.getCategoryName());
        if (categoryName == null) {
            throw new CodeBlogException("Category name - " + postDto.getCategoryName() + " does not exist");
        }
        else {
            Post post = mapDtoToPost(postDto);
            return postRepository.save(post);
        }
    }


    @Transactional(readOnly = true)
    public PostDto getSinglePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CodeBlogException("Post not found"));
        return mapToPostDto(post);
    }


    @Transactional(readOnly = true)
    public List<PostDto> getAllPost() {
        return postRepository.findAllByIsPublished(true)
                .stream()
                .map(this::mapToPostDto)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<PostDto> getAllDraftPost() {
        return postRepository.findAllByIsPublished(false)
                .stream()
                .map(this::mapToPostDto)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<PostDto> getPostByCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CodeBlogException("Category with Id - " + id + " not found"));
        List<Post> postByCategory = postRepository.findAllByCategory(category);
        return postByCategory.stream().map(this::mapToPostDto).collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<PostDto> getPostByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new CodeBlogException("Posts with username - " + username + " not found"));
        List<Post> postByUser = postRepository.findAllByUser(user);
        return postByUser.stream().map(this::mapToPostDto).collect(Collectors.toList());
    }


    @Transactional
    public Post updatePost(PostDto postDto) {
        Post existingPost = postRepository.findById(postDto.getPostId())
                .orElseThrow(()-> new CodeBlogException("Post not found"));
        User currentUser = authService.getCurrentUser();

        if (existingPost.getUser() == currentUser) {
            existingPost.setTitle(postDto.getTitle());
            existingPost.setContent(postDto.getContent());
            //existingPost.setCategory(categoryName);
            existingPost.setUser(currentUser);
            existingPost.setUpdatedDate(LocalDateTime.now());

            return postRepository.save(existingPost);
        }
        else {
            throw new CodeBlogException("You cannot edit another User's post");
        }



        //TODO -> Only the user that created the post should be able to update it
    }

    @Transactional
    public void deletePost(long id) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(()-> new CodeBlogException("Post not found"));
        User currentUser = authService.getCurrentUser();

        if (existingPost.getUser() == currentUser) {
            postRepository.deleteById(id);
        }
        else {
            throw new CodeBlogException("You cannot delete another User's post");
        }

        //TODO -> Only the user who created the post should be able to delete it
    }


    //Saves to database
    private Post mapDtoToPost(PostDto postDto) {
        Category categoryName = categoryRepository.findByName(postDto.getCategoryName());
        User currentUser = authService.getCurrentUser();
        return Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .category(categoryName)
                .user(currentUser)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .isPublished(postDto.getIsPublished())
                .build();
    }

    //reads from database
    private PostDto mapToPostDto(Post post) {
        return PostDto.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .content(post.getContent())
                .categoryName(post.getCategory().getName())
                .username(post.getUser().getUsername())
                .isPublished(post.getIsPublished())
                .createdDate(FormatTime.formatTime(post.getCreatedDate()))
                .updatedDate(FormatTime.formatTime(post.getUpdatedDate()))
                .commentCount(commentRepository.findAllByPost(post).size())
                .likesCount(likePostRepository.findAllByPost(post).size())
                .unlikesCount(unlikePostRepository.findAllByPost(post).size())
                .build();
    }


}
