package com.ecommerce.project.service;


import com.ecommerce.project.exception.APIException;
import com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.repositories.CategoryRepository;
import com.ecommerce.project.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDTO){
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        boolean isproductNotPresent = true;
        List<Product> products = category.getProducts();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductName().equalsIgnoreCase(productDTO.getProductName())){
                isproductNotPresent = false;
            }
        }
        if (isproductNotPresent) {
            Product product = modelMapper.map(productDTO, Product.class);
            product.setImage("default.png");
            product.setCategory(category);
            double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
            product.setSpecialPrice(specialPrice);
            Product savedProduct = productRepository.save(product);
            return modelMapper.map(savedProduct, ProductDTO.class);
        }else{
            throw new APIException("Product name with "+"'"+productDTO.getProductName()+"'"+" already exists");
        }
    }

    @Override
    public ProductResponse getProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortdir){
        Sort sort = sortdir.equalsIgnoreCase(sortdir) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> products = productRepository.findAll(pageRequest);
        List<Product> products1 = products.getContent();
        if (products1.isEmpty()){
            throw new APIException("No products are available");
        }
        List<ProductDTO> productDTOS = products.stream().map(product -> modelMapper.map(product, ProductDTO.class)).toList();
        ProductResponse response = new ProductResponse();
        response.setContent(productDTOS);
        response.setPageNumber(products.getNumber());
        response.setPageSize(products.getSize());
        response.setTotalElements(products.getTotalElements());
        response.setTotalPages(products.getTotalPages());
        response.setLastPage(products.isLast());
        return response;
    }

    @Override
    public ProductResponse searchByCatId(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortdir){
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        Sort sort = sortdir.equalsIgnoreCase(sortdir) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest request = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> productSort = productRepository.findByCategoryOrderByPriceAsc(category, request);
        List<Product> products = productSort.getContent();
        List<ProductDTO> productDTOS = products.stream().map(product -> modelMapper.map(product, ProductDTO.class)).toList();
        ProductResponse response = new ProductResponse();
        response.setContent(productDTOS);
        response.setPageNumber(productSort.getNumber());
        response.setPageSize(productSort.getSize());
        response.setTotalElements(productSort.getTotalElements());
        response.setTotalPages(productSort.getTotalPages());
        response.setLastPage(productSort.isLast());
        return response;
    }

    @Override
    public ProductResponse searchProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortdir) {
        Sort sort = sortdir.equalsIgnoreCase(sortdir) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest request = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> productPage = productRepository.findByProductNameLikeIgnoreCase('%'+keyword+'%', request); // select query "findBy", searchBy "ProductName", pattern matching "Like", function "ignore Case".
        List<Product> products = productPage.getContent();
        List<ProductDTO> productDTO = products.stream().map(product -> modelMapper.map(product, ProductDTO.class)).toList();
        ProductResponse response = new ProductResponse();
        response.setContent(productDTO);
        response.setPageNumber(productPage.getNumber());
        response.setPageSize(productPage.getSize());
        response.setTotalElements(productPage.getTotalElements());
        response.setTotalPages(productPage.getTotalPages());
        response.setLastPage(productPage.isLast());
        return response;
    }

    @Override
    public ProductDTO updateProductById(ProductDTO productDB, Long productId) {
        Product products = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "ProductId", productId));
        Product product = modelMapper.map(productDB, Product.class);
        products.setProductName(product.getProductName());
        products.setDescription(product.getDescription());
        products.setQuantity(product.getQuantity());
        products.setDiscount(product.getDiscount());
        products.setPrice(product.getPrice());
        products.setSpecialPrice(product.getSpecialPrice());
        Product productDTO1 = productRepository.save(products);
        return modelMapper.map(productDTO1, ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProductById(Long productId){
        Product productDB = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product", "productId", productId));
        productRepository.delete(productDB);
        return modelMapper.map(productDB, ProductDTO.class);
    }

    @Override
    public ProductDTO updateImage(Long productId, MultipartFile image) throws IOException {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        String fileName = fileService.uploadImage(path, image);

        product.setImage(fileName);
        Product updateProImg = productRepository.save(product);
        return modelMapper.map(updateProImg, ProductDTO.class);
    }

}
