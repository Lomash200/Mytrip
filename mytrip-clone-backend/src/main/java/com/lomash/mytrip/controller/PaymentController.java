package com.lomash.mytrip.controller;

import com.lomash.mytrip.common.ApiResponse;
import com.lomash.mytrip.dto.payment.PaymentRequest;
import com.lomash.mytrip.dto.payment.PaymentResponse;
import com.lomash.mytrip.service.AuthService;
import com.lomash.mytrip.service.FraudCheckService;
import com.lomash.mytrip.service.PaymentService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final FraudCheckService fraudCheckService;
    private final AuthService authService;

    public PaymentController(PaymentService paymentService,
                             FraudCheckService fraudCheckService,
                             AuthService authService) {
        this.paymentService = paymentService;
        this.fraudCheckService = fraudCheckService;
        this.authService = authService;
    }

    // =====================================
    // CREATE ORDER
    // =====================================
    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestBody PaymentRequest request, HttpServletRequest httpReq) {

        Long userId = authService.getLoggedUser().getId();
        String ip = httpReq.getRemoteAddr();

        // Fraud check
        if (fraudCheckService.isIpBlocked(ip)) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Suspicious activity detected. Try later!"));
        }

        fraudCheckService.recordPaymentAttempt(userId);

        // Create Razorpay order
        PaymentResponse response = paymentService.createOrder(request);
        return ResponseEntity.ok(response);
    }

    // =====================================
    // VERIFY PAYMENT
    // =====================================
    @PostMapping("/verify")
    public ApiResponse<String> verifyPayment(
            @RequestParam String orderId,
            @RequestParam String paymentId,
            @RequestParam String signature
    ) {
        return paymentService.verifyPayment(orderId, paymentId, signature);
    }
}
