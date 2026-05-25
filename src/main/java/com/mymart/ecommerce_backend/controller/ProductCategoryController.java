package com.mymart.ecommerce_backend.controller;

import com.mymart.ecommerce_backend.dto.ApiResponse;
import com.mymart.ecommerce_backend.dto.ProductCategoryDto;
import com.mymart.ecommerce_backend.service.ProductCategoryService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @Value("${app.images.upload-dir}")
    private String uploadDir;

    ProductCategoryDto dto = new ProductCategoryDto();
;

    @PostMapping("/product-category")
    public ResponseEntity<ApiResponse<ProductCategoryDto>> createProductCategory(@RequestBody ProductCategoryDto productCategoryDto) {

        ApiResponse<ProductCategoryDto> response = new ApiResponse();

        ProductCategoryDto productCategory = productCategoryService.createProductCategory(productCategoryDto);
        if (productCategory != null) {
            response.setStatusCode(201);
            response.setData(productCategory);
            response.setMessage("Product Category Saved");


            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            response.setStatusCode(201);
            response.setMessage("Product Category Saved Failed");
            response.setData(null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/product-categories")
    public ResponseEntity<ApiResponse<List<ProductCategoryDto>>> getAllProductCategories() {
        List<ProductCategoryDto> allProductCategories = productCategoryService.getAllProductCategories();

        ApiResponse<List<ProductCategoryDto>> response = new ApiResponse();

        response.setStatusCode(200);
        response.setMessage("Product Categories Retrieved Successfully");
        response.setData(allProductCategories);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/product-category/{categoryId}")
    public ResponseEntity<ApiResponse<ProductCategoryDto>> getProductCategoryById(@PathVariable Integer categoryId) {

        ProductCategoryDto productCategoryById = productCategoryService.getProductCategoryById(categoryId);
        ApiResponse<ProductCategoryDto> response = new ApiResponse<>();

        response.setStatusCode(200);
        response.setMessage("Product Category Retrieved");
        response.setData(productCategoryById);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/product-category/{categoryId}")
    public ResponseEntity<ApiResponse<ProductCategoryDto>> updateCategory(@PathVariable Integer categoryId, @RequestBody ProductCategoryDto categoryDto) {

        ProductCategoryDto updatedCategory =  productCategoryService.updateProductCategory(categoryId, categoryDto);
        ApiResponse<ProductCategoryDto> response = new ApiResponse<>();

        response.setStatusCode(200);
        response.setMessage("Product Category Retrieved");
        response.setData(updatedCategory);

        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/product-category/{categoryId}")
    public ResponseEntity<ApiResponse<ProductCategoryDto>> updateCategory(@PathVariable Integer categoryId) {

    ProductCategoryDto updateCategory = productCategoryService.deleteProductCategory(categoryId);
    ApiResponse<ProductCategoryDto> response = new ApiResponse<>();

        response.setStatusCode(200);
        response.setMessage("Product Category Deleted");
        response.setData(updateCategory);

        return ResponseEntity.ok(response);

    }

}
