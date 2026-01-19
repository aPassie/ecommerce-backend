package com.shanks.E_Commerce.Backend.client;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentServiceClient {

    public String createPayment(double amount) {
        return "PAY_" + UUID.randomUUID().toString().substring(0, 8);
    }

    public boolean processPayment(String paymentId) {
        return true;
    }

    public String getPaymentStatus(String paymentId) {
        return "SUCCESS";
    }
}
