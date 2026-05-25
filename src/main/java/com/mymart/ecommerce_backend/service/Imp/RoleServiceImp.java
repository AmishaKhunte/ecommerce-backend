package com.mymart.ecommerce_backend.service.Imp;

import com.mymart.ecommerce_backend.constants.AppConstants;
import com.mymart.ecommerce_backend.dto.ProductDto;
import com.mymart.ecommerce_backend.dto.RoleDto;
import com.mymart.ecommerce_backend.entities.RoleEntity;
import com.mymart.ecommerce_backend.mapper.RoleMapper;
import com.mymart.ecommerce_backend.repos.RoleRepo;
import com.mymart.ecommerce_backend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImp implements RoleService {

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public RoleDto createRole(RoleDto roleDto) {
        RoleEntity entity = RoleMapper.toEntity(roleDto);
        RoleEntity savedEntity = roleRepo.save(entity);

        return RoleMapper.toDto(savedEntity);
    }

    @Override
    public RoleDto updateRole(Integer roleId, RoleDto roleDto) {
        Optional<RoleEntity> byId = roleRepo.findById(roleId);
        if(byId.isPresent()) {
            RoleEntity roleEntity = byId.get();
            roleEntity.setName(roleDto.getName());
            roleEntity.setActiveSw(roleDto.getActiveSw());
            RoleEntity updatedEntity = roleRepo.save(roleEntity);
            return RoleMapper.toDto(updatedEntity);
        }
        return null;
    }

    @Override
    public List<RoleDto> getAllRoles(){
        return roleRepo.findAll()
                .stream()
                .map(role -> RoleMapper.toDto(role))
                .collect(Collectors.toList());
    }

    @Override
    public RoleDto getRoleById(Integer roleId){
        Optional<RoleEntity> byId = roleRepo.findById(roleId);
        if(byId.isPresent()){
            RoleEntity roleEntity = byId.get();
            return RoleMapper.toDto(roleEntity);
        }
       return  null;
    }

    @Override
    public RoleDto getRoleByName(String roleName){
       return null;
    }

    @Override
    public RoleDto deleteRole(Integer roleId){
        Optional<RoleEntity> byId = roleRepo.findById(roleId);
        if(byId.isPresent()){
            RoleEntity roleEntity = byId.get();
            roleEntity.setActiveSw(AppConstants.NO);
            RoleEntity deletedEntity = roleRepo.save(roleEntity);
            return RoleMapper.toDto(deletedEntity);
        }
        return null;
    }

}
