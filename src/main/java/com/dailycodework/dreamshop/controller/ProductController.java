package com.dailycodework.dreamshop.controller;

import com.dailycodework.dreamshop.exceptions.ResourceNotFoundException;
import com.dailycodework.dreamshop.model.Product;
import com.dailycodework.dreamshop.product.iProductService;
import com.dailycodework.dreamshop.dto.ProductDto;
import com.dailycodework.dreamshop.request.AddProductRequest;
import com.dailycodework.dreamshop.request.ProductUpdateRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final iProductService productService;

    public ProductController(iProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        List<Product> products=productService.getAllProducts();

        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable Long productId) {
        try {
            Product product = productService.getProductById(productId);
            ProductDto productDto=productService.convertToDto(product);

            return ResponseEntity.ok(productService.getProductById(productId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody AddProductRequest product) {
        try {
            return ResponseEntity.ok(productService.addProduct(product));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long productId,
            @RequestBody ProductUpdateRequest product) {

        try {
            return ResponseEntity.ok(productService.updateProduct(product, productId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        try {
            productService.deleteProductById(productId);
            return ResponseEntity.ok("Deleted successfully");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getProductByName(@PathVariable String name) {
        List<Product> products = productService.getProductsByName(name);

        return products.isEmpty()
                ? ResponseEntity.status(NOT_FOUND).body("No products found")
                : ResponseEntity.ok(products);
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<?> getProductByBrand(@PathVariable String brand) {
        List<Product> products = productService.getProductsByBrand(brand);
        List<ProductDto> convertedProducts=productService.getConvertedProducts(products);
        return products.isEmpty()
                ? ResponseEntity.status(NOT_FOUND).body("No products found")
                : ResponseEntity.ok(products);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<?> getProductByCategory(@PathVariable String category) {
        List<Product> products = productService.getProductsByCategory(category);

        return products.isEmpty()
                ? ResponseEntity.status(NOT_FOUND).body("No products found")
                : ResponseEntity.ok(products);
    }
}