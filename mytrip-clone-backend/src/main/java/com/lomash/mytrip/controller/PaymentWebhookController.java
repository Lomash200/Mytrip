package com.lomash.mytrip.controller;

import com.lomash.mytrip.common.ApiResponse;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.io.BufferedReader;

@RestController
@RequestMapping("/api/payment")
public class PaymentWebhookController {

    @Value("${razorpay.webhook.secret}")
    private String webhookSecret;

    @PostMapping("/webhook")
    public ApiResponse<?> handleWebhook(HttpServletRequest request, @RequestHeader("X-Razorpay-Signature") String signature) {

        try {
            // Read request body
            StringBuilder inputBuffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;

            while ((line = reader.readLine()) != null) {
                inputBuffer.append(line);
            }

            String requestBody = inputBuffer.toString();

            // VERIFY SIGNATURE
            boolean isValid = Utils.verifyWebhookSignature(requestBody, signature, webhookSecret);

            if (!isValid) {
                return ApiResponse.error("Invalid webhook signature");
            }

            JSONObject payload = new JSONObject(requestBody);

            // You can handle event types here:
            // payment.captured, payment.failed, order.paid, refund.processed, etc.

            return ApiResponse.ok("Webhook verified", payload.toString());

        } catch (Exception e) {
            return ApiResponse.error("Webhook error: " + e.getMessage());
        }
    }
}
