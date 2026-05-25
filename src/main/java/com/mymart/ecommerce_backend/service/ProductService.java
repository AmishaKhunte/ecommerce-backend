package com.mymart.ecommerce_backend.service;

import com.mymart.ecommerce_backend.dto.ProductCategoryDto;
import com.mymart.ecommerce_backend.dto.ProductDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    public ProductDto createProduct(Integer categoryId, ProductDto productDto, MultipartFile productImage) throws Exception;

    public ProductDto updateProduct(Integer productId, ProductDto productDto, MultipartFile productImage) throws Exception;

    public List<ProductDto> getAllProductsByCategoryId(Integer categoryId);

    public ProductDto getProductById(Integer productId);

    public List<ProductDto> getProductsByName(String productName);

    public ProductDto deleteProduct(Integer productId);
}
