package com.mymart.ecommerce_backend.config;

import com.mymart.ecommerce_backend.constants.AppConstants;
import com.mymart.ecommerce_backend.entities.RoleEntity;
import com.mymart.ecommerce_backend.repos.RoleRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    CommandLineRunner seedRoles(RoleRepo roleRepo) {
        return args -> {
            createRoleIfMissing(roleRepo, "ADMIN");
            createRoleIfMissing(roleRepo, "USER");
        };
    }

    private void createRoleIfMissing(RoleRepo roleRepo, String roleName) {
        if (roleRepo.findByNameIgnoreCase(roleName) != null) {
            return;
        }

        RoleEntity role = new RoleEntity();
        role.setName(roleName);
        role.setActiveSw(AppConstants.YES);
        roleRepo.save(role);
    }
}
