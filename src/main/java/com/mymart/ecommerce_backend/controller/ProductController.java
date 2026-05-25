package com.mymart.ecommerce_backend.controller;


import com.mymart.ecommerce_backend.dto.ApiResponse;
import com.mymart.ecommerce_backend.dto.ProductDto;
import com.mymart.ecommerce_backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/product")
    public ResponseEntity<ApiResponse<ProductDto>> createProduct(@RequestParam("categoryId") Integer categoryId,
                                                                 @RequestParam("product") String productJson,
                                                                 @RequestParam("productImage") MultipartFile productImage)
            throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        ProductDto productDto = parseProduct(productJson);
        ProductDto createdProduct = productService.createProduct(categoryId, productDto, productImage);

        ApiResponse<ProductDto> response = new ApiResponse();
        if (createdProduct != null) {
            response.setStatusCode(201);
            response.setMessage("Product created successfully");
            response.setData(createdProduct);
            return ResponseEntity.status(201).body(response);
        } else {
            response.setStatusCode(400);
            response.setMessage("Failed to create product");
            response.setData(null);
            return ResponseEntity.status(400).body(response);
        }
    }

    @PutMapping("/product")
    public ResponseEntity<ApiResponse<ProductDto>> updateProduct(@RequestParam(value = "productId", required = false) Integer productId,
                                                                 @RequestParam("product") String productJson,
                                                                 @RequestParam("productImage") MultipartFile productImage)
            throws Exception{
        ProductDto productDto = parseProduct(productJson);
        if (productId == null) {
            productId = productDto.getProductId();
        }

        if (productId == null) {
            ApiResponse<ProductDto> response = new ApiResponse<>();
            response.setStatusCode(400);
            response.setMessage("productId is required for update. Send productId as form-data or inside product JSON.");
            response.setData(null);
            return ResponseEntity.status(400).body(response);
        }

        ProductDto createdProduct = productService.updateProduct(productId, productDto, productImage);

        ApiResponse<ProductDto> response = new ApiResponse();
        if (createdProduct != null) {
            response.setStatusCode(200);
            response.setMessage("Product Updated successfully");
            response.setData(createdProduct);
            return ResponseEntity.status(200).body(response);
        } else {
            response.setStatusCode(400);
            response.setMessage("Failed to update product");
            response.setData(null);
            return ResponseEntity.status(400).body(response);
        }
    }

    @GetMapping("/products/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<ProductDto>>> getProductsByCategoryId(@PathVariable Integer categoryId){

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


    @GetMapping("/product/by-name/{name}")
    public ResponseEntity<ApiResponse<List<ProductDto>>> getProductsByName(@PathVariable String name){

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

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<ApiResponse<ProductDto>> deleteProduct(@PathVariable("productId") Integer productId)throws Exception {

        ProductDto deletedProduct = productService.deleteProduct(productId);

        ApiResponse<ProductDto> response = new ApiResponse<>();

        if (deletedProduct != null) {
            response.setStatusCode(200);
            response.setMessage("Product deleted successfully");
            response.setData(deletedProduct);
            return ResponseEntity.status(200).body(response);
        } else {
            response.setStatusCode(400);
            response.setMessage("Failed to delete product");
            response.setData(null);
            return ResponseEntity.status(400).body(response);
        }
    }

    private ProductDto parseProduct(String productJson) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(productJson, ProductDto.class);
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidProductJson() {
        ApiResponse<Object> response = new ApiResponse<>();
        response.setStatusCode(400);
        response.setMessage("Invalid product JSON. Send actual JSON in the product form-data field.");
        response.setData(null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
