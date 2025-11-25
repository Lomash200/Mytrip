package com.lomash.mytrip.controller;

import com.lomash.mytrip.dto.payment.PaymentRequest;
import com.lomash.mytrip.dto.payment.PaymentResponse;
import com.lomash.mytrip.dto.payment.PaymentVerifyRequest;
import com.lomash.mytrip.exception.ApiException;
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

    @PostMapping("/create-order")
    public ResponseEntity<PaymentResponse> createOrder(
            @RequestBody PaymentRequest request,
            HttpServletRequest httpRequest
    ) {

        Long userId = authService.getLoggedUser().getId();
        double amount = request.getAmount();

        // 🔥 IP FRAUD CHECK
        String ip = httpRequest.getRemoteAddr();
        if (fraudCheckService.isIpBlocked(ip)) {
            throw new ApiException("Your network is temporarily restricted due to suspicious activity.");
        }

        // 🔥 PAYMENT ATTEMPT CHECK
        fraudCheckService.recordPaymentAttempt(userId);

        // 🔥 HIGH-AMOUNT + MULTIPLE ATTEMPTS = FRAUD
        if (fraudCheckService.isPaymentSuspicious(userId, amount)) {
            fraudCheckService.recordFraudEvent(userId,
                    "High transaction amount with repeated attempts");
            throw new ApiException("Payment flagged as suspicious. Please try again later.");
        }

        // Continue normal order creation
        return ResponseEntity.ok(paymentService.createOrder(request));
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verify(@RequestBody PaymentVerifyRequest request) {
        return ResponseEntity.ok(paymentService.verifyPayment(request));
    }
}
