package com.lomash.mytrip.controller;

import com.lomash.mytrip.dto.coupon.CouponRequest;
import com.lomash.mytrip.dto.coupon.CouponResponse;
import com.lomash.mytrip.service.CouponService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/coupons")
public class AdminCouponController {

    private final CouponService service;

    public AdminCouponController(CouponService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CouponResponse> create(@RequestBody CouponRequest request) {
        return ResponseEntity.ok(service.createCoupon(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CouponResponse> update(
            @PathVariable Long id,
            @RequestBody CouponRequest request) {
        return ResponseEntity.ok(service.updateCoupon(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteCoupon(id);
        return ResponseEntity.ok("Coupon deleted");
    }
}
