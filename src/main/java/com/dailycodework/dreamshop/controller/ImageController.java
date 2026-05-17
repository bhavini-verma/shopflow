package com.dailycodework.dreamshop.controller;

import com.dailycodework.dreamshop.exceptions.ResourceNotFoundException;
import com.dailycodework.dreamshop.model.Image;
import com.dailycodework.dreamshop.service.iImageService;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.HttpStatus.*;


@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final iImageService imageService;

    public ImageController(iImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> saveImage(@RequestParam List<MultipartFile> files,
                                       @RequestParam Long productId) {
        try {
            var images = imageService.saveImage(files, productId);
            return ResponseEntity.ok(images);
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body("Upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/download/{imageId}")
    public ResponseEntity<ByteArrayResource> downloadImage(@PathVariable Long imageId) {

        Image image = imageService.getImageById(imageId);

        ByteArrayResource resource = new ByteArrayResource(image.getImage()); // ✅ FIXED

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + image.getFileName() + "\"")
                .body(resource);
    }

    @PutMapping("/update/{imageId}")
    public ResponseEntity<?> updateImage(@PathVariable Long imageId,
                                         @RequestParam MultipartFile file) {
        try {
            imageService.updateImage(file, imageId);
            return ResponseEntity.ok("Update successful");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
public ResponseEntity<?> deleteImage(@PathVariable Long id) {
    imageService.deleteImage(id);
    return ResponseEntity.ok("Image deleted successfully");
}
        
    }
