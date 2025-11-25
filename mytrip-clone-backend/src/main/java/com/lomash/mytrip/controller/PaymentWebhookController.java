package com.lomash.mytrip.controller;

import com.lomash.mytrip.service.PaymentWebhookService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/payments/webhook")
public class PaymentWebhookController {

    private final PaymentWebhookService webhookService;

    public PaymentWebhookController(PaymentWebhookService webhookService) {
        this.webhookService = webhookService;
    }

    @PostMapping("/razorpay")
    public ResponseEntity<String> handleRazorpay(
            HttpServletRequest request,
            @RequestHeader(value = "X-Razorpay-Signature", required = false) String signature
    ) throws Exception {

        // 🔥 Read raw payload safely
        String payload = StreamUtils.copyToString(
                request.getInputStream(),
                StandardCharsets.UTF_8
        );

        if (signature == null || signature.isBlank()) {
            return ResponseEntity.badRequest().body("Missing signature header");
        }

        webhookService.handleRazorpayWebhook(payload, signature);

        return ResponseEntity.ok("OK");
    }
}
