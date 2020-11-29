package com.codemountain.codeblog.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Integer likesCount;
    private Integer unlikesCount;
    private Integer commentCount;
}
