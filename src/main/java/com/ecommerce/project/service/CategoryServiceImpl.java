package com.ecommerce.project.service;

import com.ecommerce.project.exception.APIException;
import com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired // inOrder to skip NoArgsConstructor
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize,  String sortBy, String sortDir){
        Sort sorting = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
        PageRequest pageDetails = PageRequest.of(pageNumber, pageSize, sorting); // pageRequest requests saved contents according to the page Number and page Size
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);// retrieve categories according to the page number and page size
        List<Category> category = categoryPage.getContent();
        if (category.isEmpty()){
            throw new APIException("No Category available");
        }
        List<CategoryDTO> categoryDTOS = category.stream().map(category1 -> modelMapper.map(category1, CategoryDTO.class)).toList(); // converts the Category into DTO and storing as immutable list
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setLastPage(categoryPage.isLast());
        return categoryResponse;
    }

    @Override
    public CategoryDTO addCategory(CategoryDTO category) {
        Category category2 = modelMapper.map(category, Category.class);// converts the DTO into category
        Category category1 = categoryRepository.findByCategoryName(category.getCategoryName()); // using the findByCategoryName get the category name from the repo.func.
        if (category1 != null){
            throw new APIException("Category name with "+"'"+category.getCategoryName()+"'"+" already exists");
        }
        Category savedCategory = categoryRepository.save(category2);
        CategoryDTO categoryDTO = modelMapper.map(savedCategory, CategoryDTO.class); // converts the category into DTO
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
