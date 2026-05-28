package com.mymart.ecommerce_backend.controller;

import com.mymart.ecommerce_backend.dto.ApiResponse;
import com.mymart.ecommerce_backend.dto.ProductDto;
import com.mymart.ecommerce_backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductListingRestController {


    @Autowired
    private ProductService productService;

    @GetMapping("/products/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<ProductDto>>> getProductsByCategoryId(@PathVariable Integer categoryId) {

        List<ProductDto> allProductsByCategoryId = productService.getAllProductsByCategoryId(categoryId);
        ApiResponse<List<ProductDto>> response = new ApiResponse<>();

        if (!allProductsByCategoryId.isEmpty()) {
            response.setStatusCode(200);
            response.setMessage("Products fetched successfully");
            response.setData(allProductsByCategoryId);
            return ResponseEntity.status(200).body(response);
        } else {
            response.setStatusCode(400);
            response.setMessage("Failed to fetch products");
            response.setData(null);
            return ResponseEntity.status(400).body(response);
        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponse<ProductDto>> getProductById(@PathVariable Integer productId) {

        ProductDto productById = productService.getProductById(productId);

        ApiResponse<ProductDto> response = new ApiResponse<>();

        if (productById != null) {
            response.setStatusCode(200);
            response.setMessage("Product fetched successfully");
            response.setData(productById);
            return ResponseEntity.status(200).body(response);
        } else {
            response.setStatusCode(400);
            response.setMessage("Failed to fetch product");
            response.setData(null);
            return ResponseEntity.status(400).body(response);
        }
    }

    @GetMapping("/product/by-name/{name}")
    public ResponseEntity<ApiResponse<List<ProductDto>>> getProductsByName(@PathVariable String name) {

        List<ProductDto> productsByName = productService.getProductsByName(name);

        ApiResponse<List<ProductDto>> response = new ApiResponse<>();

        if (!productsByName.isEmpty()) {
            response.setStatusCode(200);
            response.setMessage("Products fetched successfully");
            response.setData(productsByName);
            return ResponseEntity.status(200).body(response);
        } else {
            response.setStatusCode(400);
            response.setMessage("Failed to fetch products");
            response.setData(null);
            return ResponseEntity.status(400).body(response);
        }
    }

}