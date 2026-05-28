package com.mymart.ecommerce_backend.controller;

import com.mymart.ecommerce_backend.dto.ApiResponse;
import com.mymart.ecommerce_backend.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class NotificationRestController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/offers-notification")
    public ResponseEntity<ApiResponse<String>> sendOfferNotification() {

        notificationService.sendOffersNotification();

        ApiResponse<String> response = new ApiResponse<>();

        response.setMessage("Notification sent successfully");
        response.setStatusCode(200);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/daily-delivery-updates")
    public ResponseEntity<ApiResponse<String>> sendDailyDeliveryUpdates() {

        notificationService.sendDailyDeliveryUpdates();

        ApiResponse<String> response = new ApiResponse<>();

        response.setMessage("Notification sent successfully");
        response.setStatusCode(200);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
