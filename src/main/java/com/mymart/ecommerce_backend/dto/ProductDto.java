package com.mymart.ecommerce_backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductDto {


    private Integer productId;
    private String name;
    private String description;
    private String title;
    private Double unitPrice;
    private String imageUrl;
    private String active;
    private Integer unitsStock;
    private Integer createdBy;
    private Integer updatedBy;
    private LocalDateTime dateCreated;
    private LocalDateTime lastUpdated;
}
