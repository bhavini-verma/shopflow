package com.dailycodework.dreamshop.controller;

import java.sql.SQLException;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dailycodework.dreamshop.model.Image;
import com.dailycodework.dreamshop.response.ApiRespose;
import com.dailycodework.dreamshop.service.image.iImageService;

@RestController
@RequestMapping("{api.prefix}/images")
public class ImageController {
    private final iImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ApiRespose> saveImage(@RequuestParam List<MultipartFile> files, @RequestParam Long productId) 
    try{
       List<ImageDto> imageDtos = imageService.saveImage(files, productId);
       return ResponseEntity.ok(new ApiRespose.message("Image saved successfully", imageDtos));
    }catch(Exception e){
         return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("upload failed",e.getMessage()));
    }
        
   @GetMapping("/image/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException {
    Image image = imageService.getImageById(imageId);
    ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(pos: 1, (int) image.getImage().length()));
    return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
        .body(resource);
}

    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId, @RequestBody MultipartFile file) {
    Image image = imageService.getImageById(imageId);
    if(image != null) {
        imageService.updateImage(file, imageId);
        return ResponseEntity.ok(new ApiResponse( message: "Update success!", image));
    }
}


        
    }

}
