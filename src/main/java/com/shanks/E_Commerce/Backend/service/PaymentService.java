package com.shanks.E_Commerce.Backend.service;

import com.shanks.E_Commerce.Backend.client.PaymentServiceClient;
import com.shanks.E_Commerce.Backend.model.Order;
import com.shanks.E_Commerce.Backend.model.Payment;
import com.shanks.E_Commerce.Backend.repository.OrderRepository;
import com.shanks.E_Commerce.Backend.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentServiceClient paymentServiceClient;

    public Payment createPayment(String orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            return null;
        }

        Payment existing = paymentRepository.findByOrderId(orderId).orElse(null);
        if (existing != null) {
            return existing;
        }

        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setAmount(order.getTotalAmount());

        String externalPaymentId = paymentServiceClient.createPayment(order.getTotalAmount());
        payment.setPaymentId(externalPaymentId);

        return paymentRepository.save(payment);
    }

    public Payment getPayment(String id) {
        return paymentRepository.findById(id).orElse(null);
    }

    public Payment getPaymentByOrderId(String orderId) {
        return paymentRepository.findByOrderId(orderId).orElse(null);
    }

    public Payment processPayment(String id) {
        Payment payment = paymentRepository.findById(id).orElse(null);
        if (payment != null) {
            boolean success = paymentServiceClient.processPayment(payment.getPaymentId());

            if (success) {
                payment.setStatus("SUCCESS");
                Order order = orderRepository.findById(payment.getOrderId()).orElse(null);
                if (order != null) {
                    order.setStatus("PAID");
                    orderRepository.save(order);
                }
            } else {
                payment.setStatus("FAILED");
            }
            paymentRepository.save(payment);
        }
        return payment;
    }

    public void handleWebhook(String paymentId, String status) {
        Payment payment = paymentRepository.findByPaymentId(paymentId).orElse(null);
        if (payment != null) {
            payment.setStatus(status);
            paymentRepository.save(payment);

            Order order = orderRepository.findById(payment.getOrderId()).orElse(null);
            if (order != null) {
                if (status.equals("SUCCESS")) {
                    order.setStatus("PAID");
                } else if (status.equals("FAILED")) {
                    order.setStatus("PAYMENT_FAILED");
                }
                orderRepository.save(order);
            }
        }
    }
}
