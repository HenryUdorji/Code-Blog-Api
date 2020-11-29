package com.codemountain.codeblog.controller;


import com.codemountain.codeblog.dto.ImageUploadResponse;
import com.codemountain.codeblog.entity.ImageUpload;
import com.codemountain.codeblog.service.ImageUploadService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/image")
@AllArgsConstructor
public class UserController {

    private final ImageUploadService imageUploadService;

    @PostMapping
    public ResponseEntity<ImageUploadResponse> uploadImage(@RequestParam("image")MultipartFile multipartFile) {
        ImageUpload imageUpload = imageUploadService.uploadImage(multipartFile);

        if (imageUpload != null) {
            String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/v1/image/")
                    .path(String.valueOf(imageUpload.getUploadId()))
                    .toUriString();

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    ImageUploadResponse.builder()
                    .downloadUri(downloadUrl)
                    .imageName(imageUpload.getName())
                    .imageType(imageUpload.getImageType())
                    .uploadId(imageUpload.getUploadId())
                    .userId(imageUpload.getUser().getUserId())
                    .message("Image uploaded successfully")
                    .build()
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ImageUploadResponse.builder()
                .message("Image upload failed").build()
        );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long userId) {
        ImageUpload imageUpload = imageUploadService.downloadImage(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(imageUpload.getImageType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= " + imageUpload.getName())
                .body(new ByteArrayResource(imageUpload.getImageData()));
    }


    //TODO -> Create api for updating user info/details
}
