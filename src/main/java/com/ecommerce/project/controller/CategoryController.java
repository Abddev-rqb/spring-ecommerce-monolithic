package com.ecommerce.project.controller;

import com.ecommerce.project.config.AppConstants;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/")
public class CategoryController {

    @Autowired // inOrder to skip NoArgsConstructor
    private CategoryService categoryService;

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(@RequestParam(name= "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false)Integer pageNumber,
                                                             @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false)Integer pageSize,
                                                             @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CATEGORY_BY, required = false) String sortBy,
                                                             @RequestParam(name = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir){ // retrieves the response body with CategoryResponse which contains list of DTOs
        CategoryResponse status = categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PostMapping("/admin/category")
    public ResponseEntity<CategoryDTO> addCategory(@Valid @RequestBody CategoryDTO category){// retrieves the response body with CategoryDTO which contains list of DB mapped entities
        //@Valid validates the double entries into DB
            CategoryDTO categoryDTO = categoryService.addCategory(category);
            return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
    }

    @DeleteMapping("/admin/category/{id}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long id){
            CategoryDTO status = categoryService.deleteCategory(id);
            return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PutMapping("/admin/categories/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO category, @PathVariable Long id){
            CategoryDTO statusDTO = categoryService.updateCategory(category, id);
            return new ResponseEntity<>(statusDTO, HttpStatus.OK);
    }
}
