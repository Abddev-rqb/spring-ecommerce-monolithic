package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse getAllCategories();
    void addCategory(Category category);
    String deleteCategory(Long id);
    Category updateCategory(Category category, Long id);
}
