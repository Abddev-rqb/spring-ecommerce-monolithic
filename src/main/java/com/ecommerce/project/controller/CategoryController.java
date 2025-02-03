package com.ecommerce.project.controller;


import com.ecommerce.project.model.Category;
import com.ecommerce.project.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("api/public/categories")
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> status = categoryService.getAllCategories();
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PostMapping("api/admin/category")
    public ResponseEntity<String> addCategory(@RequestBody Category category){
            categoryService.addCategory(category);
            return new ResponseEntity<>("Created Successfully", HttpStatus.OK);
    }

    @DeleteMapping("api/admin/category/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
        try{
            String status = categoryService.deleteCategory(id);
            return new ResponseEntity<>(status, HttpStatus.OK);
        }catch (ResponseStatusException e){
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }

    @PutMapping("api/admin/category/{id}")
    public ResponseEntity<String> updateCategory(@RequestBody Category category, @PathVariable Long id){
        try{
            String status = categoryService.updateCategory(category, id);
            return new ResponseEntity<>(status, HttpStatus.OK);
        }catch (ResponseStatusException e){
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }
}
