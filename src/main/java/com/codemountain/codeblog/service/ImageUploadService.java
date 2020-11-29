package com.codemountain.codeblog.service;


import com.codemountain.codeblog.entity.ImageUpload;
import com.codemountain.codeblog.exception.CodeBlogException;
import com.codemountain.codeblog.repository.ImageUploadRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@AllArgsConstructor
public class ImageUploadService {

    private final ImageUploadRepository imageUploadRepository;
    private final AuthService authService;


    @Transactional
    public ImageUpload uploadImage(MultipartFile multipartFile) {

        String imageFolderPath = "C:\\WorkFolder\\Spring\\codeblog\\images\\";

        try {
            //save to locally
            byte[] data = multipartFile.getBytes();
            Path path = Paths.get(imageFolderPath + multipartFile.getOriginalFilename());
            Files.write(path, data);

            //save to database
            ImageUpload imageUpload = new ImageUpload();
            imageUpload.setImageData(multipartFile.getBytes());
            imageUpload.setImageType(multipartFile.getContentType());
            imageUpload.setName(multipartFile.getOriginalFilename());
            imageUpload.setUser(authService.getCurrentUser());

            //delete existing image from database while saving new one
            imageUploadRepository.findByUser(authService.getCurrentUser())
                    .ifPresent(byUser -> imageUploadRepository.deleteById(byUser.getUploadId()));

            return imageUploadRepository.save(imageUpload);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional(readOnly = true)
    public ImageUpload downloadImage(Long userId) {
        ImageUpload byUser = imageUploadRepository.findByUser(authService.getCurrentUser())
                .orElseThrow(()-> new CodeBlogException("No image found for this user"));
        if (byUser.getUser().getUserId().equals(userId)) {
            ImageUpload one = imageUploadRepository.getOne(byUser.getUploadId());
            return one;
        }
        throw new CodeBlogException("Error no match in database");
        //return null;
    }
}
