package com.codemountain.codeblog.service;


import com.codemountain.codeblog.dto.CommentsDto;
import com.codemountain.codeblog.entity.Comments;
import com.codemountain.codeblog.entity.NotificationEmail;
import com.codemountain.codeblog.entity.Post;
import com.codemountain.codeblog.entity.User;
import com.codemountain.codeblog.exception.CodeBlogException;
import com.codemountain.codeblog.repository.CommentRepository;
import com.codemountain.codeblog.repository.PostRepository;
import com.codemountain.codeblog.repository.UserRepository;
import com.codemountain.codeblog.utils.FormatTime;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final AuthService authService;
    private final MailContentBuilderService mailContentBuilderService;
    private MailService mailService;

    @Transactional
    public Comments createComment(CommentsDto commentsDto) {
        Comments comments = mapDtoToComments(commentsDto);

        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(()-> new CodeBlogException("Post with Id - " + commentsDto.getPostId() + " not found"));

        String message = mailContentBuilderService.build(post.getUser().getUsername() + " posted a comment on your post.");
        sendCommentNotification(message, post.getUser());

        return commentRepository.save(comments);
    }


    @Transactional(readOnly = true)
    public List<CommentsDto> getCommentsByPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CodeBlogException("Post with Id - " + postId + " not found"));
        List<Comments> commentsByPost = commentRepository.findAllByPost(post);
        return commentsByPost.stream().map(this::mapToCommentsDto).collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<CommentsDto> getCommentsByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CodeBlogException("Username not found"));
        List<Comments> commentsByUser = commentRepository.findAllByUser(user);
        return commentsByUser.stream().map(this::mapToCommentsDto).collect(Collectors.toList());
    }


    @Transactional
    public Comments updateComment(CommentsDto commentsDto) {
        Comments existingComment = commentRepository.findById(commentsDto.getCommentId())
                .orElseThrow(()-> new CodeBlogException("Comment not found"));
        User currentUser = authService.getCurrentUser();

        if (existingComment.getUser() == currentUser) {
            existingComment.setContent(commentsDto.getContent());
            existingComment.setUser(currentUser);
            existingComment.setUpdatedDate(LocalDateTime.now());

            return commentRepository.save(existingComment);
        }
        else {
            throw new CodeBlogException("You cannot edit another User's comment");
        }
        //TODO -> Only the user that created the comment should be able to update it
    }

    @Transactional
    public void deleteComment(long id) {
        Comments existingComment = commentRepository.findById(id)
                .orElseThrow(()-> new CodeBlogException("Comment not found"));
        User currentUser = authService.getCurrentUser();

        if (existingComment.getUser() == currentUser) {
            commentRepository.deleteById(id);
        }
        else {
            throw new CodeBlogException("You cannot delete another User's comment");
        }
        //TODO -> Only the user who created the post should be able to delete it
    }


    //saves to database
    private Comments mapDtoToComments(CommentsDto commentsDto) {
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(()-> new CodeBlogException("Post with Id - " + commentsDto.getPostId() + " not found"));
        User currentUser = authService.getCurrentUser();

        return Comments.builder().post(post)
                .content(commentsDto.getContent())
                .user(currentUser)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();
    }

    //reads from database
    private CommentsDto mapToCommentsDto(Comments comments) {
        return CommentsDto.builder()
                .commentId(comments.getCommentId())
                .content(comments.getContent())
                .postId(comments.getPost().getPostId())
                .username(comments.getUser().getUsername())
                .createdDate(FormatTime.formatTime(comments.getCreatedDate()))
                .updatedDate(FormatTime.formatTime(comments.getUpdatedDate()))
                .build();
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getEmail(),
                user.getUsername() + " commented on your post", message));
    }


}
