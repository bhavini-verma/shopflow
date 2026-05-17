package com.dailycodework.dreamshop.category;

import com.dailycodework.dreamshop.model.Category;
import java.util.List;

public interface iCategoryService {

    Category getCategoryById(Long id);

    Category getCategoryByName(String name);

    List<Category> getAllCategories();

    Category addCategory(Category category);

    Category updateCategory(Long id, Category category);

    void deleteCategory(Long id);
}