package com.mymart.ecommerce_backend.service;

import com.mymart.ecommerce_backend.dto.OrderDto;

import java.util.List;

public interface NotificationService {

    // Order Confirmation after payment
    public OrderDto sendOrderConfirmation(Integer orderId);

    // Delivery Updates
    public List<OrderDto> sendDeliveryNotification(Integer orderId, String otp);

    public void sendDailyDeliveryUpdates();

    // Order Cancelled
    public OrderDto cancelledOrderNotification(Integer orderId);

    // Offers
    public void sendOffersNotification();
}