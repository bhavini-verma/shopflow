package com.dailycodework.dreamshop.service.image;

public interface iImageService {
  Image  getImageById(Long id);
  void  deleteImageById(Long id);
  List<ImageDto> saveImages(List<MultipartFile> files, Long productId);
  void updateImage(MultipartFile file, Long imageId);




}
