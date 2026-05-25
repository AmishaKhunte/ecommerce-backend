package com.mymart.ecommerce_backend.mapper;

import com.mymart.ecommerce_backend.dto.UserDto;
import com.mymart.ecommerce_backend.entities.UserEntity;
import org.modelmapper.ModelMapper;

public class UserMapper {

    public static final ModelMapper mapper = new ModelMapper();

    public static UserDto entityToDto(UserEntity entity){
        return mapper.map(entity, UserDto.class);
    }

    public static UserEntity dtoToEntity(UserDto userDto){
        return mapper.map(userDto, UserEntity.class);
    }
}
