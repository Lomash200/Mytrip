package com.lomash.mytrip.dto.coupon;

import lombok.Data;

@Data
public class CouponRequest {
    private String code;
    private Double discount;
    private Double minAmount;
    private String expiryDate; // string date input
}
