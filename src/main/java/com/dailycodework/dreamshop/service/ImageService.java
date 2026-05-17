package com.dailycodework.dreamshop.service;

import com.dailycodework.dreamshop.model.Image;
import com.dailycodework.dreamshop.model.Product;
import com.dailycodework.dreamshop.repository.ImageRepository;
import com.dailycodework.dreamshop.product.iProductService;
import com.dailycodework.dreamshop.dto.ImageDto;
import com.dailycodework.dreamshop.exceptions.ResourceNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService implements iImageService {

    private final ImageRepository imageRepository;
    private final iProductService productService;

    // ✅ Constructor Injection (IMPORTANT)
    public ImageService(ImageRepository imageRepository, iProductService productService) {
        this.imageRepository = imageRepository;
        this.productService = productService;
    }

    // ✅ Get Image by ID
    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found!"));
    }

    // ✅ Delete Image
    @Override
    public void deleteImage(Long id) {
        Image image = getImageById(id);
        imageRepository.delete(image);
    }

    // ✅ Save Images
    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);
        List<ImageDto> savedImageDtos = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(file.getBytes());
                image.setProduct(product);

                Image savedImage = imageRepository.save(image);

                String downloadUrl = "/api/images/download/" + savedImage.getId();
                savedImage.setDownloadUrl(downloadUrl);

                imageRepository.save(savedImage);

                // DTO mapping
                ImageDto imageDto = new ImageDto();
                imageDto.setImageId(savedImage.getId());
                imageDto.setImageName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());

                savedImageDtos.add(imageDto);

            } catch (IOException e) {
                throw new RuntimeException("Failed to save image: " + e.getMessage());
            }
        }

        return savedImageDtos;
    }

    // ✅ Update Image
    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(file.getBytes());
            imageRepository.save(image);
        } catch (IOException e) {
            throw new RuntimeException("Failed to update image: " + e.getMessage());
        }
    }
}