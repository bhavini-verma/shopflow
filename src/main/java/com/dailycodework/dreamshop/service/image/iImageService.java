package com.dailycodework.dreamshop.service.image;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.dailycodework.dreamshop.dto.ImageDto;
import com.dailycodework.dreamshop.model.Image;

public interface iImageService {

    Image getImageById(Long id);

    void deleteImageById(Long id);

    List<ImageDto> saveImage(List<MultipartFile> files, Long productId);

    void updateImage(MultipartFile file, Long imageId);
}