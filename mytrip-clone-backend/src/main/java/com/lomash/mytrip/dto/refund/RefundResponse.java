package com.lomash.mytrip.dto.refund;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefundResponse {

    private String refundId;
    private double amount;
    private String status;
    private String message;
}
