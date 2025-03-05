package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface ProductService {
    ProductDTO addProduct(Long categoryId, ProductDTO productDTO);
    ProductResponse getProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortdir);
    ProductResponse searchByCatId(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortdir);
    ProductResponse searchProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortdir);
    ProductDTO updateProductById(ProductDTO productDTO, Long productId);
    ProductDTO deleteProductById(Long productId);
    ProductDTO updateImage(Long productId, MultipartFile image) throws IOException;
}
