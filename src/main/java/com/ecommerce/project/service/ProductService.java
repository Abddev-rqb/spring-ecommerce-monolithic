package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;


public interface ProductService {
    ProductDTO addProduct(Long categoryId, Product product);
    ProductResponse getProduct();
    ProductResponse searchByCatId(Category categoryId);
    ProductResponse searchProductByKeyword(String keyword);
}
