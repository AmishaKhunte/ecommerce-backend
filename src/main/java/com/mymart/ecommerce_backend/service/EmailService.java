package com.mymart.ecommerce_backend.service;

public interface EmailService {

    public boolean sendEmail(String to,String subject,String body);
}
