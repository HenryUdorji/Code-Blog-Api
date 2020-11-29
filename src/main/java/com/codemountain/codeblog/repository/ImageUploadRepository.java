package com.codemountain.codeblog.repository;

import com.codemountain.codeblog.entity.ImageUpload;
import com.codemountain.codeblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageUploadRepository extends JpaRepository<ImageUpload, Long> {

    Optional<ImageUpload> findByUser(User currentUser);

}
