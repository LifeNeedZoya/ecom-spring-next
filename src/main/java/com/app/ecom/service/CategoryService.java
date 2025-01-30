package com.app.ecom.service;

import java.util.List;

import com.app.ecom.model.Category;

public interface CategoryService {
    Category addCategory(Category category);
    Category updateCategory(Category category, Long id);
    Category deleteCategory(Long categoryId);
    List<Category> getAllCategories();
    Category getCategoryById(Long id);
    
}