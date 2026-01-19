package com.shanks.E_Commerce.Backend.controller;

import com.shanks.E_Commerce.Backend.model.Payment;
import com.shanks.E_Commerce.Backend.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/{orderId}")
    public Payment createPayment(@PathVariable String orderId) {
        return paymentService.createPayment(orderId);
    }

    @GetMapping("/order/{orderId}")
    public Payment getPaymentByOrder(@PathVariable String orderId) {
        return paymentService.getPaymentByOrderId(orderId);
    }

    @GetMapping("/{id}")
    public Payment getPayment(@PathVariable String id) {
        return paymentService.getPayment(id);
    }

    @PostMapping("/{id}/pay")
    public Payment processPayment(@PathVariable String id) {
        return paymentService.processPayment(id);
    }
}
