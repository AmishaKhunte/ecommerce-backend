package com.mymart.ecommerce_backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class PurchaseOrderRequestDto {

    public UserDto user;

    public ShippingAddressDto shippingAddressDto;

    public OrderDto order;

    public List<OrderItemsDto> orderItems;
}
