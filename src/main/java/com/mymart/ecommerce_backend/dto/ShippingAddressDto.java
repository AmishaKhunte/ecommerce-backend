package com.mymart.ecommerce_backend.dto;

import lombok.Data;

@Data
public class ShippingAddressDto {

    private Integer addrId;
    private String houseNum;
    private String city;
    private String state;
    private String zipcode;
    private String country;
    private String addrType;
    private String activeSw;
}
