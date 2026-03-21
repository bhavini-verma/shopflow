package com.dailycodework.dreamshop.product;

import java.util.List;

import com.dailycodework.dreamshop.model.Product;
import com.dailycodework.dreamshop.request.ProductUpdateRequest;

public interface iProductService {
    Product addProduct(AddProductRequest product);
    
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(ProductUpdateRequest product, Long productId);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductByName(String name);
    List<Product> getProductByBrandandName(String name,String category);
    Long countProductByBrand(String category,String);



}
