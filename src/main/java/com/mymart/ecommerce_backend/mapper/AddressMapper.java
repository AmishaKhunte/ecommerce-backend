package com.mymart.ecommerce_backend.mapper;

import com.mymart.ecommerce_backend.dto.ShippingAddressDto;
import com.mymart.ecommerce_backend.entities.ShippingAddressEntity;
import org.modelmapper.ModelMapper;

public class AddressMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static ShippingAddressEntity doToEntity(ShippingAddressDto shippingAddressDto) {
        return modelMapper.map(shippingAddressDto, ShippingAddressEntity.class);
    }
        public static ShippingAddressDto doToDto(ShippingAddressEntity shippingAddressEntity){
        return modelMapper.map(shippingAddressEntity, ShippingAddressDto.class);
        }


}
