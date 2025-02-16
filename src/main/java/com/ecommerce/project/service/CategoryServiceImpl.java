package com.ecommerce.project.service;

import com.ecommerce.project.exception.APIException;
import com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories(){
        List<Category> category = categoryRepository.findAll();
        if (category.isEmpty()){
            throw new APIException("No Category available");
        }
        return categoryRepository.findAll();
    }

    @Override
    public void addCategory(Category category) {
        Category category1 = categoryRepository.findByCategoryName(category.getCategoryName());
        if (category1 != null){
            throw new APIException("Category name with "+category1+" already exists");
        }
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long id){
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category","CategoryId",id));
        categoryRepository.delete(category);
        return "Deleted Id no. "+id+" Successfully";
    }

    @Override
    public Category updateCategory(Category category, Long id){
        Category category1 = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category","CategoryId",id));
        category1.setCategoryId(id);
        return categoryRepository.save(category);
    }
}
