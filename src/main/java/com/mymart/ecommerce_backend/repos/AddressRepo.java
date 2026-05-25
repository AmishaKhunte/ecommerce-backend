package com.mymart.ecommerce_backend.repos;

import com.mymart.ecommerce_backend.entities.ShippingAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepo extends JpaRepository<ShippingAddressEntity, Integer> {

    public List<ShippingAddressEntity> findByUserUserId(Integer userId);
}
