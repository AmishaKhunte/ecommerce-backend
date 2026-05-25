package com.mymart.ecommerce_backend.mapper;

import com.mymart.ecommerce_backend.dto.ProductDto;
import com.mymart.ecommerce_backend.entities.ProductEntity;
import org.modelmapper.ModelMapper;

public class ProductMapper {

    private  static final  ModelMapper modelMapper = new ModelMapper();

    public static ProductDto toDto(ProductEntity productEntity){
        return modelMapper.map(productEntity, ProductDto.class);
    }

    public static ProductEntity toEntity(ProductDto productDto){
        return modelMapper.map(productDto, ProductEntity.class);
    }
}
