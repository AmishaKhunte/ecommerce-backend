package com.mymart.ecommerce_backend.controller;


import com.mymart.ecommerce_backend.dto.ApiResponse;
import com.mymart.ecommerce_backend.dto.RoleDto;
import com.mymart.ecommerce_backend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/role")
    public ResponseEntity<ApiResponse<RoleDto>> createRole(@RequestBody RoleDto roleDto){
        RoleDto role = roleService.createRole(roleDto);

        ApiResponse<RoleDto> response = new ApiResponse<>();
        response.setStatusCode(201);
        response.setMessage("Role Created successfully");
        response.setData(role);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/roles")
    public ResponseEntity<ApiResponse<List<RoleDto>>> getAllReoles(){

        List<RoleDto> allRoles = roleService.getAllRoles();
        ApiResponse<List<RoleDto>> response = new ApiResponse<>();
        response.setStatusCode(200);
        response.setMessage("Roles Fetched successfully");
        response.setData(allRoles);

        return ResponseEntity.ok(response);
    }
}
