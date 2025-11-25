package com.lomash.mytrip.service.impl;

import com.lomash.mytrip.dto.coupon.CouponRequest;
import com.lomash.mytrip.dto.coupon.CouponResponse;
import com.lomash.mytrip.entity.Coupon;
import com.lomash.mytrip.exception.ApiException;
import com.lomash.mytrip.repository.CouponRepository;
import com.lomash.mytrip.service.CouponService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;

    public CouponServiceImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public CouponResponse createCoupon(CouponRequest request) {
        if (couponRepository.findByCode(request.getCode()).isPresent()) {
            throw new ApiException("Coupon code already exists");
        }

        Coupon coupon = Coupon.builder()
                .code(request.getCode())
                .discount(request.getDiscount())
                .minAmount(request.getMinAmount())
                .expiryDate(LocalDateTime.parse(request.getExpiryDate()))
                .active(true)
                .build();

        couponRepository.save(coupon);

        return map(coupon);
    }

    @Override
    public CouponResponse updateCoupon(Long id, CouponRequest request) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new ApiException("Coupon not found"));

        coupon.setCode(request.getCode());
        coupon.setDiscount(request.getDiscount());
        coupon.setMinAmount(request.getMinAmount());
        coupon.setExpiryDate(LocalDateTime.parse(request.getExpiryDate()));

        couponRepository.save(coupon);

        return map(coupon);
    }

    @Override
    public void deleteCoupon(Long id) {
        couponRepository.deleteById(id);
    }

    @Override
    public CouponResponse validateAndApply(String code, Double amount) {

        Coupon coupon = couponRepository.findByCode(code)
                .orElseThrow(() -> new ApiException("Invalid coupon code"));

        if (!coupon.getActive()) {
            throw new ApiException("Coupon is not active");
        }

        if (coupon.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new ApiException("Coupon expired");
        }

        if (amount < coupon.getMinAmount()) {
            throw new ApiException("Amount below minimum requirement");
        }

        return CouponResponse.builder()
                .id(coupon.getId())
                .code(coupon.getCode())
                .discount(coupon.getDiscount())
                .minAmount(coupon.getMinAmount())
                .expiryDate(coupon.getExpiryDate().toString())
                .active(coupon.getActive())
                .build();
    }

    private CouponResponse map(Coupon c) {
        return CouponResponse.builder()
                .id(c.getId())
                .code(c.getCode())
                .discount(c.getDiscount())
                .minAmount(c.getMinAmount())
                .expiryDate(c.getExpiryDate().toString())
                .active(c.getActive())
                .build();
    }
}
