package com.mymart.ecommerce_backend.service;

import com.mymart.ecommerce_backend.dto.OrderDto;
import com.mymart.ecommerce_backend.dto.PurchaseOrderRequestDto;
import com.mymart.ecommerce_backend.dto.PurchaseOrderResponseDto;

import java.util.List;

public interface OrderService {

    public PurchaseOrderResponseDto createOrder(PurchaseOrderRequestDto orderReqDto) throws Exception;

    public OrderDto updateOrder(Integer orderId, OrderDto orderDto);

    public OrderDto cancelOrder(Integer orderId) throws Exception;

    public OrderDto trackOrder(Integer orderId);

    public List<OrderDto> getCustomerOrders(String email);

}
