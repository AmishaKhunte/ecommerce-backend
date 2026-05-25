package com.mymart.ecommerce_backend.dto;

import lombok.Data;

@Data
public class OrderItemsDto {
    private Integer orderItemId;
    private String imageUrl;
    private Double unitPrice;
    private Integer quantity;
    private Integer productId;
}
