package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{
    private List<Category> categories = new ArrayList<>();
    private Long nextId = 1L;

    @Override
    public List<Category> getAllCategories(){
        return categories;
    }

    @Override
    public void addCategory(Category category) {
        category.setCategoryId(nextId++);
        categories.add(category);
    }

    @Override
    public String deleteCategory(Long id){
        Category category = categories.stream().filter(c -> c.getCategoryId().equals(id)).findFirst().orElse(null);
        categories.remove(category);
        if (category == null){
            return null;
        }
        return "Category Id "+id+" deleted Successfullly";
    }

    @Override
    public String updateCategory(Category category, Long id){
        Optional<Category> categoryOptional = categories.stream().filter(c -> c.getCategoryId().equals(id)).findFirst();
        if (categoryOptional.isPresent()){
            Category existingCategory = categoryOptional.get();
            existingCategory.setCategoryName(category.getCategoryName());
            return "Updated Successfully";
        }else {
            return "No resource found";
        }

    }
}
