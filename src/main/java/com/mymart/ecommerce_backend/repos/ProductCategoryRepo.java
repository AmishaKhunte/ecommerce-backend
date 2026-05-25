package com.mymart.ecommerce_backend.repos;

import com.mymart.ecommerce_backend.entities.ProductCategoryEntity;
import com.mymart.ecommerce_backend.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepo extends JpaRepository<ProductCategoryEntity, Integer> {

    public ProductCategoryEntity findByCategoryName(String categoryName);
}
