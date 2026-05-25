package com.mymart.ecommerce_backend.service.Imp;

import com.mymart.ecommerce_backend.constants.AppConstants;
import com.mymart.ecommerce_backend.dto.ProductCategoryDto;
import com.mymart.ecommerce_backend.entities.ProductCategoryEntity;
import com.mymart.ecommerce_backend.mapper.ProductCategoryMapper;
import com.mymart.ecommerce_backend.repos.ProductCategoryRepo;
import com.mymart.ecommerce_backend.service.ProductCategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCategoryServiceImp implements ProductCategoryService {

    @Autowired
    private ProductCategoryRepo productCategoryRepo;

    @Override
    public ProductCategoryDto createProductCategory(ProductCategoryDto productCategoryDto) {

        productCategoryDto.setActiveSw(AppConstants.YES);

        ProductCategoryEntity productCategoryEntity = new ProductCategoryEntity();
        productCategoryEntity.setCategoryName(productCategoryDto.getCategoryName());
        productCategoryEntity.setCategoryDescription(productCategoryDto.getCategoryDescription());
        productCategoryEntity.setActiveSw(productCategoryDto.getActiveSw());
        productCategoryEntity.setCreatedBy(toInteger(productCategoryDto.getCreatedBy()));
        productCategoryEntity.setUpdatedBy(toInteger(productCategoryDto.getUpdatedBy()));

        ProductCategoryEntity savedEntity = productCategoryRepo.save(productCategoryEntity);

        return ProductCategoryMapper.toDto(savedEntity);

    }

    @Override
    public List<ProductCategoryDto> getAllProductCategories() {

        List<ProductCategoryEntity> entities = productCategoryRepo.findAll();

        return entities.stream().map(ProductCategoryMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public ProductCategoryDto getProductCategoryById(Integer categoryId) {

        ProductCategoryEntity productCategoryEntity = productCategoryRepo.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Product category not found with id: " + categoryId));

        return ProductCategoryMapper.toDto(productCategoryEntity);
    }

    public ProductCategoryDto getProductCategoryByName(String categoryName) {

        ProductCategoryEntity productCategoryEntity = productCategoryRepo.findByCategoryName(categoryName);

        if (productCategoryEntity != null) {
            return ProductCategoryMapper.toDto(productCategoryEntity);
        }

        return null;
    }

    @Override
    public ProductCategoryDto updateProductCategory(Integer categoryId, ProductCategoryDto productCategoryDto) {

        ProductCategoryEntity existingEntity = productCategoryRepo.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Product category not found with id: " + categoryId));

        existingEntity.setCategoryName(productCategoryDto.getCategoryName());
        existingEntity.setCategoryDescription(productCategoryDto.getCategoryDescription());
        existingEntity.setActiveSw(productCategoryDto.getActiveSw());
        existingEntity.setUpdatedBy(toInteger(productCategoryDto.getUpdatedBy()));

        ProductCategoryEntity updatedEntity = productCategoryRepo.save(existingEntity); //UPSERT

        return ProductCategoryMapper.toDto(updatedEntity);

    }

    @Override
    public ProductCategoryDto deleteProductCategory(Integer categoryId) {

        ProductCategoryEntity existingEntity = productCategoryRepo.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Product category not found with id: " + categoryId));

        // productCategoryRepository.delete(existingEntity);

        existingEntity.setActiveSw(AppConstants.NO);
        // existingEntity.setUpdatedBy(updatedBy);
        ProductCategoryEntity updatedEntity = productCategoryRepo.save(existingEntity);//UPSERT

        return ProductCategoryMapper.toDto(updatedEntity);
    }

    private Integer toInteger(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}
