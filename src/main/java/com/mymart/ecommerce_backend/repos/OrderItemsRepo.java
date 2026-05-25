package com.mymart.ecommerce_backend.repos;

import com.mymart.ecommerce_backend.entities.OrderItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepo extends JpaRepository<OrderItemsEntity, Integer> {
}
