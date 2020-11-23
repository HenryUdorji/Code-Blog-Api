package com.codemountain.codeblog.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentsDto {

    private Long commentId;
    private Long postId;
    private String content;
    private String username;
    private Long createdDate;
    private Long updatedDate;
}
