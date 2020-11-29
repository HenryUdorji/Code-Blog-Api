package com.codemountain.codeblog.controller;

import com.codemountain.codeblog.dto.AuthResponse;
import com.codemountain.codeblog.dto.LoginRequest;
import com.codemountain.codeblog.dto.RefreshTokenRequest;
import com.codemountain.codeblog.dto.RegisterRequest;
import com.codemountain.codeblog.service.AuthService;
import com.codemountain.codeblog.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        log.info("User Account created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body("User Account created successfully");
    }

    @PostMapping("/login")
    public AuthResponse loginUser(@RequestBody LoginRequest loginRequest) {
        log.info("User login successful");
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh/token")
    public AuthResponse refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK).body("Refresh Token deleted successfully");
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        log.info("Account verification successful");
        return ResponseEntity.status(HttpStatus.OK).body("Account verification successful");
    }

}
