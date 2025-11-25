package com.lomash.mytrip.dto.payment;

import lombok.Data;

@Data
public class PaymentRequest {
    private Long bookingId;
    private double amount;
}
