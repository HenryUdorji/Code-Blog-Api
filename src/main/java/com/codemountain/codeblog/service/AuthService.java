package com.codemountain.codeblog.service;

import com.codemountain.codeblog.dto.AuthResponse;
import com.codemountain.codeblog.dto.LoginRequest;
import com.codemountain.codeblog.dto.RegisterRequest;
import com.codemountain.codeblog.entity.NotificationEmail;
import com.codemountain.codeblog.entity.User;
import com.codemountain.codeblog.entity.VerificationToken;
import com.codemountain.codeblog.exception.CodeBlogException;
import com.codemountain.codeblog.repository.UserRepository;
import com.codemountain.codeblog.repository.VerificationRepository;
import com.codemountain.codeblog.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationRepository verificationRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;


    /**
     * This method registers new User then a confirmation email would be sent to
     * that email. if the user confirms the email the account would be activated.
     */
    @Transactional
    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setUsername(registerRequest.getUsername());
        user.setCreatedDate(System.currentTimeMillis());
        user.setIsEnabled(false);
        user.setBio("Am a Tech enthusiast and I love Code-Blog");

        userRepository.save(user);
        String token = generateUserToken(user);
        mailService.sendMail(new NotificationEmail(user.getEmail(),
                "Please Activate your account",
                "Thank you for joining Code-Blog, " + "Please click on the link below to activate your account : " +
                        "http://localhost:8080/api/v1/auth/accountVerification/" + token));
    }


    /**
     *This generate a random UUID that would be persisted in the
     * database just in case the user does not confirm the link
     * immediately.
     */
    private String generateUserToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationRepository.findByToken(token);
        verificationToken.orElseThrow(()-> new CodeBlogException("Invalid token"));
        fetchAndEnableUser(verificationToken.get());
    }


    /**
     * Method called only when the user has successfully
     * activated their email address
     * After account activation delete the token from database
     */
    @Transactional
    void fetchAndEnableUser(VerificationToken verificationToken) {
        String email = verificationToken.getUser().getEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CodeBlogException("User not found with email - " + email));
        user.setIsEnabled(true);

        userRepository.save(user);
        verificationRepository.deleteById(verificationToken.getTokenId());
    }

    /**
     * Log in existing user and validate the login
     * using JSON Web Token
     * @return
     */
    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        return new AuthResponse(loginRequest.getEmail(), token);
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(principal.getUsername())
                .orElseThrow(()-> new UsernameNotFoundException("User not found - " + principal.getUsername()));
    }
}
