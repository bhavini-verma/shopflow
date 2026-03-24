package com.dailycodework.dreamshop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dailycodework.dreamshop.category.iCategoryService;
import com.dailycodework.dreamshop.exceptions.AlreadyExistsException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("{api.prefix}/categories")
public class CategoryController {
    private final iCategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(new ApiResponse(message:"Found!",categories));
    }
    catch(Exception e){
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(message:"Error: ",INTERNAL_SERVER_ERROR));

    }
    @PostMapping("/add")

    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category name){
        Category theCategory = categoryService.addCategory(name);
        return ResponseEntity.ok(new ApiResponse(message:"Category added successfully!",theCategory));
    }catch(AlreadyExistsException e){
        return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(),data:null));
}   

    @GetMapping("category/{id}/category")
    public ResponseEntity<ApiResponse> addCategoryById(@PathVariable Long id)
    try{
        Category theCategory = categoryService.getCategoryById(id);
        return ResponseEntity.ok(new ApiResponse(message:"Found",theCategory));
    }catch(ResourceNotFoundException e){
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),data:null));    

    }

    @GetMapping("{name}/category")
    public ResponseEntity<ApiResponse> addCategoryByName(@PathVariable String name)
    try{
        Category theCategory = categoryService.getCategoryByName(name);
        return ResponseEntity.ok(new ApiResponse(message:"Found",theCategory));
    }catch(ResourceNotFoundException e){
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),data:null));    

    }
    @DeleteMapping("category/{id}/category")
    public ResponseEntity<ApiResponse> deleteCategoryById(@PathVariable Long id)
    try{
        Category theCategory = categoryService.getCategoryById(id);
        return ResponseEntity.ok(new ApiResponse(message:"Found",theCategory));
    }catch(ResourceNotFoundException e){
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),data:null));    

    }
    

}
