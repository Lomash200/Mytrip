package com.lomash.mytrip.dto.refund;

import lombok.Data;

@Data
public class RefundRequest {
    private Long bookingId;
    private String reason;
}
