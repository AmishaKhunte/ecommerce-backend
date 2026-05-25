package com.mymart.ecommerce_backend.repos;

import com.mymart.ecommerce_backend.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo  extends JpaRepository<UserEntity, Integer> {

    public UserEntity findByEmailAndPwd(String email, String pwd);

    public UserEntity findByEmail(String email);
}
