package com.lomash.mytrip.service;

import com.lomash.mytrip.dto.coupon.CouponRequest;
import com.lomash.mytrip.dto.coupon.CouponResponse;

public interface CouponService {
    CouponResponse createCoupon(CouponRequest request);
    CouponResponse updateCoupon(Long id, CouponRequest request);
    void deleteCoupon(Long id);
    CouponResponse validateAndApply(String couponCode, Double bookingAmount);
}
