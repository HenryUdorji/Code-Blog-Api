package com.codemountain.codeblog.service;

import com.codemountain.codeblog.dto.UserDto;
import com.codemountain.codeblog.entity.User;
import com.codemountain.codeblog.repository.UserRepository;
import com.codemountain.codeblog.utils.FormatTime;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private AuthService authService;

    @Transactional
    public void uploadImage(MultipartFile multipartFile) {
        String imageFolderPath = "C:\\WorkFolder\\Spring\\codeblog\\images\\";
        Long userId = authService.getCurrentUser().getUserId();

        try {
            //save to locally
            byte[] data = multipartFile.getBytes();
            Path path = Paths.get(imageFolderPath +  userId + "_" + multipartFile.getOriginalFilename());
            Files.write(path, data);

            //save to database
            String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/v1/user/image/")
                    .path(String.valueOf(userId))
                    .toUriString();

            User user = userRepository.findById(userId).orElse(null);
            if (user != null) {
                user.setImage(downloadUrl);
                userRepository.save(user);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Transactional(readOnly = true)
    public UserDto userInfo() {
        Long userId = authService.getCurrentUser().getUserId();
        User user = userRepository.findById(userId).orElse(null);
        return UserDto.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .bio(user.getBio())
                .createdDate(FormatTime.formatTime(user.getCreatedDate()))
                .image(user.getImage())
                .isEnabled(user.getIsEnabled())
                .build();


    }

    public UserDto userImage(String url) {
        return UserDto.builder().image(url).build();
    }
}
