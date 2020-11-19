package com.codemountain.codeblog.repository;

import com.codemountain.codeblog.entity.Category;
import com.codemountain.codeblog.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);
}
