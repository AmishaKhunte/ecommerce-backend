package com.mymart.ecommerce_backend.repos;

import com.mymart.ecommerce_backend.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<ProductEntity,Integer> {

    public List<ProductEntity> findByCategoryCategoryId(Integer categoryId);

    public List<ProductEntity> findByNameContainingIgnoreCase(String name);
}
