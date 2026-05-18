package com.dailycodework.dreamshop.product;

import com.dailycodework.dreamshop.dto.ImageDto;
import com.dailycodework.dreamshop.dto.ProductDto;
import com.dailycodework.dreamshop.exceptions.ProductNotFoundException;
import com.dailycodework.dreamshop.model.Category;
import com.dailycodework.dreamshop.model.Image;
import com.dailycodework.dreamshop.model.Product;
import com.dailycodework.dreamshop.repository.CategoryRepository;
import com.dailycodework.dreamshop.repository.ImageRepository;
import com.dailycodework.dreamshop.repository.ProductRepository;
import com.dailycodework.dreamshop.request.AddProductRequest;
import com.dailycodework.dreamshop.request.ProductUpdateRequest;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements iProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;

    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository,
                          ImageRepository imageRepository,
                          ModelMapper modelMapper) {

        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.imageRepository = imageRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Product addProduct(AddProductRequest request) {
        Category category = null;
        if (request.getCategory() != null && request.getCategory().getName() != null) {
            category = categoryRepository.findByName(request.getCategory().getName());
        }
        if (category == null) {
            Category newCategory = new Category();
            if (request.getCategory() != null) {
                newCategory.setName(request.getCategory().getName());
            }
            category = categoryRepository.save(newCategory);
        }

        Product product = createProduct(request, category);
        return productRepository.save(product);
    }

    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getDescription(),
                request.getPrice(),
                request.getInventory(),
                category
        );
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found!"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(
                        productRepository::delete,
                        () -> {
                            throw new ProductNotFoundException("Product not found!");
                        }
                );
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
        Product existingProduct = getProductById(productId);
        Product updatedProduct = updateExistingProduct(existingProduct, request);
        return productRepository.save(updatedProduct);
    }

    private Product updateExistingProduct(Product existingProduct,
                                          ProductUpdateRequest request) {

        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());

        Category category = null;
        if (request.getCategory() != null && request.getCategory().getName() != null) {
            category = categoryRepository.findByName(request.getCategory().getName());
        }

        existingProduct.setCategory(category);

        return existingProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category,
                                                       String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand,
                                                   String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand,
                                            String name) {
        return productRepository.countByBrandAndName(brand, name);
    }

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products) {
        List<ProductDto> converted = new java.util.ArrayList<ProductDto>();
        for (Product product : products) {
            converted.add(convertToDto(product));
        }
        return converted;
    }

    @Override
    public ProductDto convertToDto(Product product) {
        ProductDto productDto = new ProductDto();
        modelMapper.map(product, productDto);

        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDto> imageDtos = new java.util.ArrayList<ImageDto>();
        for (Image image : images) {
            ImageDto imageDto = new ImageDto();
            modelMapper.map(image, imageDto);
            imageDtos.add(imageDto);
        }

        productDto.setImages(imageDtos);
        return productDto;
    }
}