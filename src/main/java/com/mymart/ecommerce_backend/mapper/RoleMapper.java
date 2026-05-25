package com.mymart.ecommerce_backend.mapper;

import com.mymart.ecommerce_backend.dto.RoleDto;
import com.mymart.ecommerce_backend.entities.RoleEntity;
import org.modelmapper.ModelMapper;

public class RoleMapper {

    private static ModelMapper modelMapper= new ModelMapper();

    public static RoleDto toDto(RoleEntity roleEntity){
        return modelMapper.map(roleEntity,RoleDto.class);
    }

    public static RoleEntity toEntity (RoleDto roleDto){
        return modelMapper.map(roleDto, RoleEntity.class);
    }

}
