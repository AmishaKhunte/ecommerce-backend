package com.mymart.ecommerce_backend.repos;

import com.mymart.ecommerce_backend.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepo extends JpaRepository<OrderEntity, Integer> {

    public List<OrderEntity> findByUserEmail(String email);

    public List<OrderEntity> findByDeliveryDateBetween(
            LocalDateTime start,
            LocalDateTime end
    );

}
