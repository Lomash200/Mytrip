package com.lomash.mytrip.dto.payment;

import lombok.Data;

@Data
public class PaymentVerifyRequest {
    private String razorpayPaymentId;
    private String razorpayOrderId;
    private String razorpaySignature;
    private Long bookingId;
}
