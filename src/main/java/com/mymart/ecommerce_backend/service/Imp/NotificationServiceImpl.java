package com.mymart.ecommerce_backend.service.Imp;

import com.mymart.ecommerce_backend.dto.OrderDto;
import com.mymart.ecommerce_backend.entities.OrderEntity;
import com.mymart.ecommerce_backend.entities.UserEntity;
import com.mymart.ecommerce_backend.repos.OrderRepo;
import com.mymart.ecommerce_backend.repos.UserRepo;
import com.mymart.ecommerce_backend.service.EmailService;
import com.mymart.ecommerce_backend.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepo userRepo;

    @Override
    public void sendDailyDeliveryUpdates() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.plusDays(1).atStartOfDay();

        List<OrderEntity> orders =
                orderRepo.findByDeliveryDateBetween(
                        startOfDay,
                        endOfDay
                );

        orders.forEach(order -> {
            String deliveryOTP = generateOTP(6);
            Optional<OrderEntity> byId = orderRepo.findById(order.getOrderId());
            if (byId.isPresent()) {
                OrderEntity orderEntity = byId.get();
                orderEntity.setDeliveryOtp(deliveryOTP);
                orderRepo.save(orderEntity);
            }
            UserEntity user = order.getUser();
            String subject = "Your Order is out for Delivery - MyMart";
            String body = "To confirm your delivery use OTP : " + deliveryOTP;
            emailService.sendEmail(user.getEmail(), subject, body);
        });
    }

    @Override
    public List<OrderDto> sendDeliveryNotification(Integer orderId, String otp){

        OrderEntity orderEntity = orderRepo.findById(orderId).orElseThrow();

        if(otp.equals(orderEntity.getDeliveryOtp())){
            orderEntity.setOrderStatus("DELIVERED");
            orderRepo.save(orderEntity);

            String email = orderEntity.getUser().getEmail();

            String subject = "Your Order Delivered";
            String body = "Thank you for shopping with us.... have a great day..!!";

            emailService.sendEmail(email, subject, body);
        }

        return List.of();
    }

    @Override
    public OrderDto sendOrderConfirmation(Integer orderId) {

        OrderEntity orderEntity = orderRepo.findById(orderId).orElseThrow();

        String email = orderEntity.getUser().getEmail();

        String subject = "Your Order Confirmed";
        String body = "Thank you for shopping with us.... have a great day..!!";

        emailService.sendEmail(email, subject, body);

        return null;
    }

    @Override
    public OrderDto cancelledOrderNotification(Integer orderId) {

        OrderEntity orderEntity = orderRepo.findById(orderId).orElseThrow();

        String email = orderEntity.getUser().getEmail();

        String subject = "Your Order Cancelled";
        String body = "Your refund amount will be created in 4 working days..!!";

        emailService.sendEmail(email, subject, body);

        return null;
    }

    @Override
    public void sendOffersNotification() {
        List<UserEntity> users = userRepo.findAll();
        users.forEach(user -> {
            String subject = "Big Billion Day Starts From Tomorrow - MyMart";
            String body = "50% Offer Sale on all products..";
            emailService.sendEmail(user.getEmail(), subject, body);
        });
    }

    private String generateOTP(int pwdLength) {
        Random random = new Random();
        String chars = "0123456789";
        StringBuilder buffer = new StringBuilder(pwdLength);
        for (int i = 0; i < pwdLength; i++) {
            int randomIndex = random.nextInt(chars.length());
            char charAt = chars.charAt(randomIndex);
            buffer.append(charAt);
        }
        return buffer.toString();
    }
}


