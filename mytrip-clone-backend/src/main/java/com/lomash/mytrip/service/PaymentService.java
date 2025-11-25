package com.lomash.mytrip.service;

import com.lomash.mytrip.dto.payment.PaymentRequest;
import com.lomash.mytrip.dto.payment.PaymentResponse;
import com.lomash.mytrip.dto.payment.PaymentVerifyRequest;

public interface PaymentService {
    PaymentResponse createOrder(PaymentRequest request);
    String verifyPayment(PaymentVerifyRequest request);
}
