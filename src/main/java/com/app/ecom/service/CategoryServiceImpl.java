package com.app.ecom.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.ecom.exception.NotFoundException;
import com.app.ecom.model.Category;
import com.app.ecom.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;
    @Override
    public Category addCategory(Category category) {
        Category categoryAdded = Category
                .builder()
                .name(category.getName())
                .img(category.getImg())
                .build();
        return categoryRepository.save(categoryAdded);
    }

    @Override
    public Category updateCategory(Category category, Long id) {
        Category existingCategory = categoryRepository.findById(id).orElseThrow(()->
                new NotFoundException("Category not found with this id ")
        );
        if (category.getName() != null) {
            existingCategory.setName(category.getName());
            
        }

        if (category.getImg() != null) {
            existingCategory.setImg(category.getImg());
            
        }
        return categoryRepository.save(existingCategory);
    }

    @Override
    public Category deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
        return null;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(()->
                new NotFoundException("Category not found with this id ")
        );
    }
}
