package com.dailycodework.dreamshop.controller;

import com.dailycodework.dreamshop.category.iCategoryService;
import com.dailycodework.dreamshop.exceptions.ResourceAlreadyExistsException;
import com.dailycodework.dreamshop.exceptions.ResourceNotFoundException;
import com.dailycodework.dreamshop.model.Category;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final iCategoryService categoryService;

    public CategoryController(iCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public ResponseEntity<java.lang.Object> getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            return new ResponseEntity<java.lang.Object>(categories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<java.lang.Object>("Error fetching categories", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<java.lang.Object> addCategory(@RequestBody Category category) {
        try {
            Category saved = categoryService.addCategory(category);
            return new ResponseEntity<java.lang.Object>(saved, HttpStatus.OK);
        } catch (ResourceAlreadyExistsException e) {
            return new ResponseEntity<java.lang.Object>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<java.lang.Object> getCategoryById(@PathVariable Long id) {
        try {
            Category category = categoryService.getCategoryById(id);
            return new ResponseEntity<java.lang.Object>(category, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<java.lang.Object>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<java.lang.Object> getCategoryByName(@PathVariable String name) {
        try {
            Category category = categoryService.getCategoryByName(name);
            return new ResponseEntity<java.lang.Object>(category, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<java.lang.Object>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<java.lang.Object> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            return new ResponseEntity<java.lang.Object>("Category deleted successfully", HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<java.lang.Object>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}