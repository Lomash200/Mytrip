package com.lomash.mytrip.dto.coupon;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponResponse {
    private Long id;
    private String code;
    private Double discount;
    private Double minAmount;
    private String expiryDate;
    private boolean active;
}
