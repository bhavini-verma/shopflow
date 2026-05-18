package com.dailycodework.dreamshop.controller;

import com.dailycodework.dreamshop.exceptions.ResourceNotFoundException;
import com.dailycodework.dreamshop.model.Product;
import com.dailycodework.dreamshop.product.iProductService;
import com.dailycodework.dreamshop.dto.ProductDto;
import com.dailycodework.dreamshop.request.AddProductRequest;
import com.dailycodework.dreamshop.request.ProductUpdateRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@EnableMethodSecurity
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final iProductService productService;

    public ProductController(iProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<java.lang.Object> getAllProducts() {
        return new ResponseEntity<java.lang.Object>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<java.lang.Object> getProductById(@PathVariable Long productId) {
        try {
            Product product = productService.getProductById(productId);
            ProductDto productDto = productService.convertToDto(product);
            return new ResponseEntity<java.lang.Object>(productDto, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<java.lang.Object>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")

    @PostMapping("/add")
    public ResponseEntity<java.lang.Object> addProduct(@RequestBody AddProductRequest product) {
        try {
            Product saved = productService.addProduct(product);
            return new ResponseEntity<java.lang.Object>(saved, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<java.lang.Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")

    @PutMapping("/{productId}")
    public ResponseEntity<java.lang.Object> updateProduct(
            @PathVariable Long productId,
            @RequestBody ProductUpdateRequest product) {
        try {
            Product updated = productService.updateProduct(product, productId);
            return new ResponseEntity<java.lang.Object>(updated, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<java.lang.Object>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")

    @DeleteMapping("/{productId}")
    public ResponseEntity<java.lang.Object> deleteProduct(@PathVariable Long productId) {
        try {
            productService.deleteProductById(productId);
            return new ResponseEntity<java.lang.Object>("Deleted successfully", HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<java.lang.Object>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<java.lang.Object> getProductByName(@PathVariable String name) {
        List<Product> products = productService.getProductsByName(name);
        if (products.isEmpty()) {
            return new ResponseEntity<java.lang.Object>("No products found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<java.lang.Object>(products, HttpStatus.OK);
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<java.lang.Object> getProductByBrand(@PathVariable String brand) {
        List<Product> products = productService.getProductsByBrand(brand);
        if (products.isEmpty()) {
            return new ResponseEntity<java.lang.Object>("No products found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<java.lang.Object>(products, HttpStatus.OK);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<java.lang.Object> getProductByCategory(@PathVariable String category) {
        List<Product> products = productService.getProductsByCategory(category);
        if (products.isEmpty()) {
            return new ResponseEntity<java.lang.Object>("No products found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<java.lang.Object>(products, HttpStatus.OK);
    }
}