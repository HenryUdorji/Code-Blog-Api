package com.codemountain.codeblog.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {

    private Long postId;
    private String title;
    private String content;
    private String categoryName;
    private String username;
    private String createdDate;
    private String updatedDate;
    private Boolean isPublished;
    private Integer likesCount;
    private Integer unlikesCount;
    private Integer commentCount;
}
