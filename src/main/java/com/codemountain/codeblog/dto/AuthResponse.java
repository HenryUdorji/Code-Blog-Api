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
public class AuthResponse {

    private String email;
    private String authenticationToken;
    private String refreshToken;
    private LocalDateTime expiresAt;

}
