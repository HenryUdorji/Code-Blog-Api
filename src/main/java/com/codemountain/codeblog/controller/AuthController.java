package com.codemountain.codeblog.controller;

import com.codemountain.codeblog.dto.AuthResponse;
import com.codemountain.codeblog.dto.LoginRequest;
import com.codemountain.codeblog.dto.RegisterRequest;
import com.codemountain.codeblog.entity.User;
import com.codemountain.codeblog.repository.UserRepository;
import com.codemountain.codeblog.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody RegisterRequest registerRequest) {
        User existingUser = userRepository.findByEmailIgnoreCase(registerRequest.getEmail());
        if (existingUser != null) {
            log.error("Email already exist");
            return new ResponseEntity<>("Email already exist", HttpStatus.CONFLICT);
        }
        else {
            authService.signup(registerRequest);
            log.info("User Account created successfully");
            return new ResponseEntity<>("User Account created successfully", HttpStatus.CREATED);
        }
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        log.info("Account verification successful");
        return new ResponseEntity<>("Account verification successful", HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthResponse loginUser(@RequestBody LoginRequest loginRequest) {
        log.info("User login successful");
        return authService.login(loginRequest);
    }

    //TODO -> Create api for updating user info/details
}
