package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse getAllCategories(Integer pageNumber,Integer pageSize, String sortBy, String sortDir); // while giving response to client use categoryResponse
    CategoryDTO addCategory(CategoryDTO categoryDTO); // passes arguments to the category DTO not to the DB to promote loose coupling
    CategoryDTO deleteCategory(Long id);
    CategoryDTO updateCategory(CategoryDTO category, Long id);
}
