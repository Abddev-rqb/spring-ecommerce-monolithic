package com.ecommerce.project.controller;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("api/")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(){
        CategoryResponse status = categoryService.getAllCategories();
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PostMapping("admin/category")
    public ResponseEntity<CategoryDTO> addCategory(@Valid @RequestBody CategoryDTO category){
            CategoryDTO categoryDTO = categoryService.addCategory(category);
            return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
    }

    @DeleteMapping("admin/category/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
            String status = categoryService.deleteCategory(id);
            return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PutMapping("admin/category/{id}")
    public ResponseEntity<Category> updateCategory(@Valid @RequestBody Category category, @PathVariable Long id){
            Category status = categoryService.updateCategory(category, id);
            return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
