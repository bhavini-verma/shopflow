package com.dailycodework.dreamshop.controller;

import com.dailycodework.dreamshop.exceptions.ResourceNotFoundException;
import com.dailycodework.dreamshop.model.Image;
import com.dailycodework.dreamshop.service.iImageService;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final iImageService imageService;

    public ImageController(iImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<java.lang.Object> saveImage(@RequestParam List<MultipartFile> files,
                                       @RequestParam Long productId) {
        try {
            var images = imageService.saveImage(files, productId);
            return new ResponseEntity<java.lang.Object>(images, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<java.lang.Object>("Upload failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download/{imageId}")
    public ResponseEntity<ByteArrayResource> downloadImage(@PathVariable Long imageId) {
        Image image = imageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(image.getImage());
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(image.getFileType()));
        headers.setContentDispositionFormData("attachment", image.getFileName());
        
        return new ResponseEntity<ByteArrayResource>(resource, headers, HttpStatus.OK);
    }

    @PutMapping("/update/{imageId}")
    public ResponseEntity<java.lang.Object> updateImage(@PathVariable Long imageId,
                                         @RequestParam MultipartFile file) {
        try {
            imageService.updateImage(file, imageId);
            return new ResponseEntity<java.lang.Object>("Update successful", HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<java.lang.Object>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<java.lang.Object> deleteImage(@PathVariable Long id) {
        try {
            imageService.deleteImage(id);
            return new ResponseEntity<java.lang.Object>("Image deleted successfully", HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<java.lang.Object>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
