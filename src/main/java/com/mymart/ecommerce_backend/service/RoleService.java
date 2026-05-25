package com.mymart.ecommerce_backend.service;

import com.mymart.ecommerce_backend.dto.ProductDto;
import com.mymart.ecommerce_backend.dto.RoleDto;
import org.hibernate.jdbc.Expectation;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RoleService {

   public RoleDto createRole(RoleDto roleDto);

   public RoleDto updateRole(Integer roleId, RoleDto roleDto);

   public List<RoleDto> getAllRoles();

   public RoleDto getRoleById(Integer roleId);

   public RoleDto getRoleByName(String roleName);

   public RoleDto deleteRole(Integer roleId);
}
