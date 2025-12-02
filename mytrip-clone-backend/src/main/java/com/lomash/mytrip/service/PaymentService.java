package com.lomash.mytrip.service;

import com.lomash.mytrip.common.ApiResponse;
import com.lomash.mytrip.dto.payment.PaymentRequest;
import com.lomash.mytrip.dto.payment.PaymentResponse;

public interface PaymentService {

    // Create Razorpay order from PaymentRequest
    PaymentResponse createOrder(PaymentRequest request);

    // Verify Razorpay signature to confirm payment success
    ApiResponse<String> verifyPayment(String orderId, String paymentId, String signature);

}
