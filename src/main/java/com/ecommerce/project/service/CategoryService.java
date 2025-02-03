package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    void addCategory(Category category);
    String deleteCategory(Long id);
    Category updateCategory(Category category, Long id);
}
