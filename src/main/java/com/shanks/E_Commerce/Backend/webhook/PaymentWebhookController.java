package com.shanks.E_Commerce.Backend.webhook;

import com.shanks.E_Commerce.Backend.dto.PaymentWebhookRequest;
import com.shanks.E_Commerce.Backend.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/webhooks")
public class PaymentWebhookController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/payment")
    public String handlePaymentWebhook(@RequestBody PaymentWebhookRequest request) {
        if (request.getPaymentId() != null && request.getStatus() != null) {
            paymentService.handleWebhook(request.getPaymentId(), request.getStatus());
            return "OK";
        }
        return "ERROR";
    }
}
