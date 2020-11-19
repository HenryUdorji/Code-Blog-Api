package com.codemountain.codeblog.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "TOKEN_TBL")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;

    private String token;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    //private Instant expiryDate;
}
