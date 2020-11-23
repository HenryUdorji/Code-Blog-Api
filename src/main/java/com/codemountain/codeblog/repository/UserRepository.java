package com.codemountain.codeblog.repository;

import com.codemountain.codeblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmailIgnoreCase(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    //Optional<org.springframework.security.core.userdetails.User> findByEmailIgnoreCase2(String email);
}
