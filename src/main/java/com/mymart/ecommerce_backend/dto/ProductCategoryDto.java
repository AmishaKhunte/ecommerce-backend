package com.mymart.ecommerce_backend.dto;


import lombok.Data;

@Data
public class ProductCategoryDto {

    private Integer categoryId;
    private String categoryName;
    private String categoryDescription;
    private String activeSw;
    private String createdBy;
    private String createdDate;
    private String updatedBy;
    private String updatedDate;
}
