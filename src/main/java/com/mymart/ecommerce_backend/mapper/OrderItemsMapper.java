package com.mymart.ecommerce_backend.mapper;

import com.mymart.ecommerce_backend.dto.OrderDto;
import com.mymart.ecommerce_backend.dto.OrderItemsDto;
import com.mymart.ecommerce_backend.entities.OrderItemsEntity;
import org.modelmapper.ModelMapper;

public class OrderItemsMapper {

    private static ModelMapper modelMapper= new ModelMapper();

    public  static OrderItemsDto entityToDto(OrderItemsEntity  itemsEntity){
        return modelMapper.map( itemsEntity, OrderItemsDto.class);
    }

    public static OrderItemsEntity entityToDto(OrderItemsDto itemsDto){
        return modelMapper.map(itemsDto, OrderItemsEntity.class);    }
}
