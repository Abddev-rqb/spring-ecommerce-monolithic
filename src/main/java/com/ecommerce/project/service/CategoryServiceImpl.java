package com.ecommerce.project.service;

import com.ecommerce.project.exception.APIException;
import com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
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
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(){
        List<Category> category = categoryRepository.findAll();
        if (category.isEmpty()){
            throw new APIException("No Category available");
        }
        List<CategoryDTO> categoryDTOS = category.stream().map(category1 -> modelMapper.map(category1, CategoryDTO.class)).toList();
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        return categoryResponse;
    }

    @Override
    public CategoryDTO addCategory(CategoryDTO category) {
        Category category2 = modelMapper.map(category, Category.class);
        Category category1 = categoryRepository.findByCategoryName(category.getCategoryName());
        if (category1 != null){
            throw new APIException("Category name with "+"'"+category.getCategoryName()+"'"+" already exists");
        }
        Category savedCategory = categoryRepository.save(category2);
        CategoryDTO categoryDTO = modelMapper.map(savedCategory, CategoryDTO.class);
        return categoryDTO;
    }

    @Override
    public CategoryDTO deleteCategory(Long id){
        Category category1 = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category","CategoryId",id));
        categoryRepository.delete(category1);
        return modelMapper.map(category1, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO category, Long id){
        Category category1 = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category","CategoryId",id));
        Category category2 = modelMapper.map(category, Category.class);
        category2.setCategoryId(id);
        category1 = categoryRepository.save(category2);
        return modelMapper.map(category1, CategoryDTO.class);
    }
}
