package com.codemountain.codeblog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CategoryDto {

    private Long categoryId;
    private String name;
    private String description;
    private Integer numberOfPosts;
    private LocalDateTime createdDate;
}
