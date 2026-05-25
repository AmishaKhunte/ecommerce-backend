package com.mymart.ecommerce_backend.dto;


import lombok.Data;

@Data
public class PurchaseOrderResponseDto {

    private Integer orderId;

    private String razorpayOrderId;

    private String orderStatus;

    private String orderTrackingNumber;

    private String paymentStatus;

}
