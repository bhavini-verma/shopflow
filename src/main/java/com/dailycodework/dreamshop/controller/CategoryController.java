package com.dailycodework.dreamshop.controller;

import com.dailycodework.dreamshop.category.iCategoryService;
import com.dailycodework.dreamshop.exceptions.ResourceAlreadyExistsException;
import com.dailycodework.dreamshop.exceptions.ResourceNotFoundException;
import com.dailycodework.dreamshop.model.Category;
import com.dailycodework.dreamshop.model.Image;
import com.dailycodework.dreamshop.service.iImageService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final iCategoryService categoryService;

    public CategoryController(iCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body("Error fetching categories");
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody Category category) {
        try {
            Category saved = categoryService.addCategory(category);
            return ResponseEntity.ok(saved);
        } catch (ResourceAlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        try {
            Category category = categoryService.getCategoryById(id);
            return ResponseEntity.ok(category);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getCategoryByName(@PathVariable String name) {
        try {
            Category category = categoryService.getCategoryByName(name);
            return ResponseEntity.ok(category);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok("Category deleted successfully");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        }
    }
}