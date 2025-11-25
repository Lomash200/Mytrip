package com.lomash.mytrip.controller;

import com.lomash.mytrip.dto.coupon.CouponResponse;
import com.lomash.mytrip.service.CouponService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/coupon")
public class CouponApplyController {

    private final CouponService service;

    public CouponApplyController(CouponService service) {
        this.service = service;
    }

    @GetMapping("/apply")
    public ResponseEntity<CouponResponse> applyCoupon(
            @RequestParam String code,
            @RequestParam Double amount) {

        return ResponseEntity.ok(service.validateAndApply(code, amount));
    }
}
