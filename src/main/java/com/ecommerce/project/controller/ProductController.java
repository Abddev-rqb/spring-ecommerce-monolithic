package com.ecommerce.project.controller;


import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    private ResponseEntity<ProductDTO> addProduct(@PathVariable Long categoryId, @RequestBody Product product){
        ProductDTO productDTO1 = productService.addProduct(categoryId, product);
        return new ResponseEntity<>(productDTO1, HttpStatus.CREATED);
    }

    @GetMapping("/public/products")
    private ResponseEntity<ProductResponse> getProducts(){
        ProductResponse productResponse = productService.getProduct();
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/public/category/{categoryId}/product")
    private ResponseEntity<ProductResponse> getByCatId(@PathVariable Category categoryId){
        ProductResponse productResponse = productService.searchByCatId(categoryId);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/public/products/keyword/{keyword}")
    private ResponseEntity<ProductResponse> getProductByKeyword(@PathVariable String keyword){
        ProductResponse productResponse = productService.searchProductByKeyword(keyword);
        return new ResponseEntity<>(productResponse, HttpStatus.FOUND);
    }

    @PutMapping("admin/products/{productId}")
    private ResponseEntity<ProductDTO> updateProduct(@RequestBody Product product, @PathVariable Long productId){
        ProductDTO productDTO1 = productService.updateProductById(product, productId);
        return new ResponseEntity<>(productDTO1, HttpStatus.OK);
    }

    @DeleteMapping("admin/products/{productId}")
    private ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId){
        ProductDTO productDTO = productService.deleteProductById(productId);
        return new ResponseEntity<>(productDTO, HttpStatus.GONE);
    }
}
