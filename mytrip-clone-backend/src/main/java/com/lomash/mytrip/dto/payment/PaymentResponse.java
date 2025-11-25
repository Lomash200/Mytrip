package com.lomash.mytrip.dto.payment;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {
    private String orderId;
    private double amount;
    private String currency;
}
