package com.codemountain.codeblog.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "UNLIKE_POST_TBL")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UnlikePost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long unlikeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId", referencedColumnName = "postId")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;
}
