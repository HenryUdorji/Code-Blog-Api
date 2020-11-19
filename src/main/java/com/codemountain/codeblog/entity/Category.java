package com.codemountain.codeblog.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CATEGORY_TBL")
@Data
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    @NotBlank(message = "Category name is required")
    private String name;
    @NotBlank(message = "Description is required")
    private String description;
    private Instant createdOn;
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Post> categoryPost = new ArrayList<>();

}
