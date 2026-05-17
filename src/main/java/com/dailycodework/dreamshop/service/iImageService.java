package com.dailycodework.dreamshop.service;

import com.dailycodework.dreamshop.dto.ImageDto;
import com.dailycodework.dreamshop.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface iImageService {

    Image getImageById(Long id);

    void deleteImage(Long id);

    List<ImageDto> saveImage(List<MultipartFile> files, Long productId);

    void updateImage(MultipartFile file, Long imageId);
}