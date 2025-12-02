package com.lomash.mytrip.dto.payment;

import lombok.Data;

@Data
public class PaymentRequest {
    private double amount;
    private String currency = "INR";
    private Long bookingId;   // ✅ Important: Add this
    private String receipt;
}