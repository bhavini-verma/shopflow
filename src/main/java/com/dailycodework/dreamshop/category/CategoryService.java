package com.dailycodework.dreamshop.category;

import com.dailycodework.dreamshop.exceptions.ResourceAlreadyExistsException;
import com.dailycodework.dreamshop.exceptions.ResourceNotFoundException;
import com.dailycodework.dreamshop.model.Category;
import com.dailycodework.dreamshop.repository.CategoryRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service

public class CategoryService implements iCategoryService {

    private final CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
}

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category)
            .filter(c -> !categoryRepository.existsByName(c.getName()))
            .map(categoryRepository::save)
            .orElseThrow(() -> new ResourceAlreadyExistsException(category.getName() + " already exists!"));
    }

    @Override
    public Category updateCategory(Long id, Category category) {
        return Optional.ofNullable(getCategoryById(id))
            .map(oldCategory -> {
                oldCategory.setName(category.getName());
                return categoryRepository.save(oldCategory);
            })
            .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.findById(id)
            .ifPresentOrElse(categoryRepository::delete,
                () -> { throw new ResourceNotFoundException("Category not found!"); });
    }
}