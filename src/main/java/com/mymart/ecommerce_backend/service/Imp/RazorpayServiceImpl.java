package com.mymart.ecommerce_backend.service.Imp;

import com.mymart.ecommerce_backend.service.RazorPayService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Refund;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RazorpayServiceImpl implements RazorPayService {

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    private RazorpayClient razorpayClient;

    @Override
    public String createRazorpayOrder(Double amount) throws Exception{

        this.razorpayClient = new RazorpayClient(keyId, keySecret);

        JSONObject orderReq = new JSONObject();
        orderReq.put("amount", Math.round(amount * 100));
        orderReq.put("currency", "INR");
        orderReq.put("payment_capture",1);

        Order order = razorpayClient.orders.create(orderReq);
        return order.get("id");
    }

    @Override
    public String refundPayment(String paymentId, Double amount) throws  Exception{

        this.razorpayClient= new RazorpayClient(keyId,keySecret);

        JSONObject refundReq = new JSONObject();
        refundReq.put("payment_id",paymentId);
        refundReq.put("amount", Math.round(amount * 100));

        Refund refund = razorpayClient.payments.refund(refundReq);
        return refund.get("id");

    }

}
