package com.dailycodework.dreamshop.service;
import com.dailycodework.dreamshop.model.Image;

import java.io.IOException;

import org.springframework.strereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredargsConstructor

public class ImageService implements iImageService {

    
    private final ImageRepository imageRepository;
    private final iProductService productService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Image not found!"));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.deleteById(id)ifPresentorElse(imageRepository::delete,()->{
            throw new ResourceNotFoundException("Image not found!: "+id);
        });
    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {
        Product product= productService.getProductById(productId);
        List<ImageDto> savedImageDto= new ArrayList<>();
        for(MultipartFile file: files){
            try {
                Image image= new Image();
                image.setFilename(file.getOriginalFilename());
                image.setFileName(file.getOriginalFilename());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);
               
                String buillDowloadUrl= "api/v1/images/image/dowload/"+image.getId();
                String dowloadUrl= buillDowloadUrl+image.getId();
                image.setDowloadUrl(dowloadUrl);
                Image savedImage=imageRepository.save(image);

                savedImage.setDowloadUrl(buillDowloadUrl+savedImage.getId());
                imageRepository.save(savedImage);

                ImageDto imageDto1= new ImageDto();
                imageDto.setImageId(savedImage.getId());
                imageDto.setImageName(savedImage.getFileName());
                imageDto.setDowloadUrl(savedImage.getDowloadUrl());
                savedImage.setDowloadUrl(savedImage.getDowloadUrl());
                savedImageDto.add(imageDto);
            }
            catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImageDto;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
       Image image= getImageById(imageId);
        try {
            image.setFilename(file.getOriginalFilename());
            image.setFileName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        }
        catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
