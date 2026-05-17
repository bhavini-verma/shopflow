package com.dailycodework.dreamshop.product;

import com.dailycodework.dreamshop.model.Product;
import java.util.List;

import com.dailycodework.dreamshop.dto.ProductDto;
import com.dailycodework.dreamshop.request.AddProductRequest;
import com.dailycodework.dreamshop.request.ProductUpdateRequest;

public interface iProductService {

    List<Product> getAllProducts();

    Product getProductById(Long id);

    Product addProduct(AddProductRequest request);

    Product updateProduct(ProductUpdateRequest request, Long id);

    void deleteProductById(Long id);

    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String brand, String name);
    List<ProductDto> getConvertedProducts(List<Product> products);
    ProductDto convertToDto(Product product);
}