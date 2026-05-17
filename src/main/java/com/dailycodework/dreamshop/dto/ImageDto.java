package com.dailycodework.dreamshop.dto;

public class ImageDto {

    private Long imageId;
    private String imageName;
    private String downloadUrl;

    // getters
    public Long getImageId() {
        return imageId;
    }

    public String getImageName() {
        return imageName;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    // setters
    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}