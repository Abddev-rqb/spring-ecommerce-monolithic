package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;


public interface ProductService {
    ProductDTO addProduct(Long categoryId, ProductDTO productDTO);
    ProductResponse getProduct();
    ProductResponse searchByCatId(Category categoryId);
    ProductResponse searchProductByKeyword(String keyword);
    ProductDTO updateProductById(ProductDTO productDTO, Long productId);
    ProductDTO deleteProductById(Long productId);
}
