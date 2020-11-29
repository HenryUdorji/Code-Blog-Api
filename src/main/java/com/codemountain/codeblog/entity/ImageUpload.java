package com.codemountain.codeblog.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "IMAGE_UPLOAD_TBL")
@Data
@Builder
public class ImageUpload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uploadId;
    private String name;
    private String imageType;
    @Lob
    private byte[] imageData;
    @OneToOne
    @JoinColumn(name = "UserId")
    private User user;
}
