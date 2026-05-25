package com.mymart.ecommerce_backend.mapper;

import com.mymart.ecommerce_backend.dto.OrderDto;
import com.mymart.ecommerce_backend.entities.OrderEntity;
import org.modelmapper.ModelMapper;

public class OrderMapper {

    private static ModelMapper modelMapper = new ModelMapper();

    public static OrderDto toDto(OrderEntity orderEntity){
        return modelMapper.map(orderEntity, OrderDto.class);
    }

    public static OrderEntity toEntity(OrderDto orderDto){
        return modelMapper.map(orderDto, OrderEntity.class);
    }
}
