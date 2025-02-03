package com.ecommerce.project.controller;


import com.ecommerce.project.model.Category;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoryController {
    private List<Category> categories = new ArrayList<>();

    @GetMapping("api/public/categories")
    public List<Category> getAllCategories(){
        return categories;
    }

    @PostMapping("api/admin/category")
    public String addCategory(@RequestBody Category category){
        categories.add(category);
        return "Created Successfully";
    }
}
