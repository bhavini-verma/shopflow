package com.dailycodework.dreamshop.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dailycodework.dreamshop.exceptions.ResourceNotFoundException;
import com.dailycodework.dreamshop.model.Product;
import com.dailycodework.dreamshop.product.iProductService;
import com.dailycodework.dreamshop.response.ApiRespose;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("{api.prefix}/products")
public class ProductController {

    private final iProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiRespose> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(new ApiRespose("success", products));

    }
    @GetMapping("product/{productId}/product")
    public ResponseEntity<ApiRespose> getProductById(@PathVariable productId) {
        try{Product product = productService.getProductById(productId);
        return ResponseEntity.ok(new ApiRespose("success", product));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new ApiRespose(e.getMessage(), null));
        }
        public ResponseEntity<ApiRespose> addProduct(@RequestBody AddProductRequest product)
        {
            try{
                Product theProduct = productService.addProduct(product);
                return ResponseEntity.ok(new ApiRespose("add product success", theProduct));
            }catch (Exception e) {
                return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiRespose(e.getMessage(), null));
            } 


            @GetMapping("/product/{productId}/update")
            public responseEntity<ApiRespose> updateProduct(@RequestBody UpdateProductRequest product, @PathVariable  Long productId)
            try{
               Product   theProduct = productService.updateProduct(product, productId);
               return ResponseEntity.ok(new ApiRespose("update product success", theProduct));
            }catch (Exception e) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiRespose(e.getMessage(), null));
            }
            }
            @DeleteMapping("/product/{productId}/delete")
            public ResponseEntity<ApiRespose> deleteProduct(@PathVariable Long productId)
            {
                try{
                    productService.deleteProduct(productId);
                    return ResponseEntity.ok(new ApiRespose("delete product success", productId));
                }catch (Exception e) {
                    return ResponseEntity.status(NOT_FOUND).body(new ApiRespose(e.getMessage(), null));
                }
            }
            @GetMapping("/products/by/brand-and-name")
            public ResponseEntity<ApiRespose> getProductByBrandandName(@RequestParam String brandName, @RequestParam String productName)
           try {
                List<Product> products = productService.getProductByBrandandName(brandName, productName);
                if(products.isEmpty()){
                    return ResponseEntity.status(NOT_FOUND).body(new ApiRespose("No products found for brand: ", null));
                }
            
            return ResponseEntity.ok(new ApiRespose("success", products));
           }catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiRespose(e.getMessage(), null));
           }

           @GetMapping("/products/by/category-and-brand")
            public ResponseEntity<ApiRespose> getProductByCategoryandBrand(@PathVariable String brandName, @PathVariable String brand)
           try {
                List<Product> products = productService.getProductByCategoryandBrand(category,brand);
                if(products.isEmpty()){
                    return ResponseEntity.status(NOT_FOUND).body(new ApiRespose("No products found for brand: ", null));
                }
            
             return ResponseEntity.ok(new ApiRespose("success", products));
           }catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiRespose(e.getMessage(), null));
           }

           @GetMapping("/products/{name}/products")
            public ResponseEntity<ApiRespose> getProductByName(@PathVariable String name)
           try {
                List<Product> products = productService.getProductByName(name);
                if(products.isEmpty()){
                    return ResponseEntity.status(NOT_FOUND).body(new ApiRespose("No products found for brand: ", null));
                }
            
             return ResponseEntity.ok(new ApiRespose("success", products));
           }catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiRespose(e.getMessage(), null));
           }


           @GetMapping("/products/by/brand")
            public ResponseEntity<ApiRespose> getProductByBrand(@PathVariable String brand)
           try {
                List<Product> products = productService.getProductByBrand(brand);
                if(products.isEmpty()){
                    return ResponseEntity.status(NOT_FOUND).body(new ApiRespose("No products found for brand: ", null));
                }
            
             return ResponseEntity.ok(new ApiRespose("success", products));
           }catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiRespose(e.getMessage(), null));
           }


            @GetMapping("/products/{categor}/all/products")
            public ResponseEntity<ApiRespose> getProductByCategory(@PathVariable String category)
           try {
                List<Product> products = productService.getProductByCategory(category);
                if(products.isEmpty()){
                    return ResponseEntity.status(NOT_FOUND).body(new ApiRespose("No products found for brand: ", null));
                }
            
             return ResponseEntity.ok(new ApiRespose("success", products));
           }catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiRespose(e.getMessage(), null));
           }


           @GetMapping("/product/count/by-brand/and-name")
            public ResponseEntity<ApiRespose> countProductByBrandandName(@RequestParam String brand, @RequestParam String name)
           try {
                List<Product> products = productService.getProductByBrandandName(brand, name);
                if(products.isEmpty()){
                    return ResponseEntity.status(NOT_FOUND).body(new ApiRespose("No products found for brand: ", null));
                }
            
             return ResponseEntity.ok(new ApiRespose("success", products));
           }catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiRespose(e.getMessage(), null));
           }
        }


    }


