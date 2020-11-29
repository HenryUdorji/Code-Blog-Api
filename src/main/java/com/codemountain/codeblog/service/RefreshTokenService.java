package com.codemountain.codeblog.service;


import com.codemountain.codeblog.entity.RefreshToken;
import com.codemountain.codeblog.exception.CodeBlogException;
import com.codemountain.codeblog.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;


    public RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());
        return refreshTokenRepository.save(refreshToken);
    }


    public void validateRefreshToken(String token) {
        refreshTokenRepository.findByToken(token)
                .orElseThrow(()-> new CodeBlogException("Invalid Refresh Token"));
    }


    public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }
}
