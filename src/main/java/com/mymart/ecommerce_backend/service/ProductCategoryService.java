package com.mymart.ecommerce_backend.service;

import com.mymart.ecommerce_backend.dto.ProductCategoryDto;

import java.util.List;

public interface ProductCategoryService {

    public ProductCategoryDto createProductCategory(ProductCategoryDto categoryDto);

    public List<ProductCategoryDto> getAllProductCategories();

    public ProductCategoryDto getProductCategoryById(Integer categoryId);

    public ProductCategoryDto updateProductCategory(Integer categoryId, ProductCategoryDto categoryDto);

    public ProductCategoryDto deleteProductCategory(Integer categoryId);
}
