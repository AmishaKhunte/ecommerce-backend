//package com.mymart.ecommerce_backend.service.Imp;
//
//import com.mymart.ecommerce_backend.service.RazorPayService;
//import org.springframework.beans.factory.annotation.Value;
//
//public class RazorpayServiceImpl implements RazorPayService {
//
//    @Value("${razorpay.key.id}")
//    private String keyId;
//
//    @Value("${razorpay.key.secret}")
//    private String keySecret;
//
//    private RazorpayClient razorpayClient;
//
//    @Override
//    public String createRazorpayOrder(Double amount) throws Exception{
//
//        this.razorpayClient = new RazorpayClient(keyId, keySecret);
//        J
//    }
//
//}
