package com.ecommerce.project.service;


import com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.repositories.CategoryRepository;
import com.ecommerce.project.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDTO addProduct(Long categoryId, Product product){
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        product.setImage("default.png");
        product.setCategory(category);
        double specialPrice = product.getPrice() -((product.getDiscount() * 0.01) * product.getPrice());
        product.setSpecialPrice(specialPrice);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductResponse getProduct(){
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOS = products.stream().map(product -> modelMapper.map(product, ProductDTO.class)).toList();
        ProductResponse response = new ProductResponse();
        response.setContent(productDTOS);
        return response;
    }

    @Override
    public ProductResponse searchByCatId(Category categoryId){
        List<Product> products = productRepository.findByCategoryOrderByPriceAsc(categoryId);
        List<ProductDTO> productDTOS = products.stream().map(product -> modelMapper.map(product, ProductDTO.class)).toList();
        ProductResponse response = new ProductResponse();
        response.setContent(productDTOS);
        return response;
    }

    @Override
    public ProductResponse searchProductByKeyword(String keyword) {
        List<Product> products = productRepository.findByProductNameLikeIgnoreCase('%'+keyword+'%'); // select query "findBy", searchBy "ProductName", pattern matching "Like", function "ignore Case".
        List<ProductDTO> productDTO = products.stream().map(product -> modelMapper.map(product, ProductDTO.class)).toList();
        ProductResponse response = new ProductResponse();
        response.setContent(productDTO);
        return response;
    }

    @Override
    public ProductDTO updateProductById(Product productDB, Long productId) {
        Product products = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "ProductId", productId));
        products.setProductName(productDB.getProductName());
        products.setDescription(productDB.getDescription());
        products.setQuantity(productDB.getQuantity());
        products.setDiscount(productDB.getDiscount());
        products.setPrice(productDB.getPrice());
        products.setSpecialPrice(productDB.getSpecialPrice());
        Product productDTO1 = productRepository.save(products);
        return modelMapper.map(productDTO1, ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProductById(Long productId){
        Product productDB = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product", "productId", productId));
        productRepository.delete(productDB);
        return modelMapper.map(productDB, ProductDTO.class);
    }
}
