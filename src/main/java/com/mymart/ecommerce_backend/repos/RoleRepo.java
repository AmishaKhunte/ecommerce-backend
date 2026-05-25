package com.mymart.ecommerce_backend.repos;

import com.mymart.ecommerce_backend.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<RoleEntity, Integer> {

    public RoleEntity findByName(String name);

    public RoleEntity findByNameIgnoreCase(String name);
}
