package com.lomash.mytrip.service.impl;

import com.lomash.mytrip.exception.ApiException;
import com.lomash.mytrip.service.FraudCheckService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
public class FraudCheckServiceImpl implements FraudCheckService {

    private final Map<Long, Integer> bookingAttempts = new HashMap<>();
    private final Map<Long, Integer> paymentAttempts = new HashMap<>();
    private final Map<Long, Instant> userBlockTime = new HashMap<>();
    private final Map<String, Integer> ipAttempts = new HashMap<>();

    private static final int MAX_BOOKING_ATTEMPTS = 5;
    private static final int MAX_PAYMENT_ATTEMPTS = 5;
    private static final int MAX_IP_ATTEMPTS = 20;

    private static final long BLOCK_DURATION_SECONDS = 3600; // 1 hour block

    @Override
    public boolean isUserBlocked(Long userId) {
        if (!userBlockTime.containsKey(userId)) return false;

        Instant blockTime = userBlockTime.get(userId);
        if (Instant.now().isAfter(blockTime.plusSeconds(BLOCK_DURATION_SECONDS))) {
            userBlockTime.remove(userId);
            return false;
        }
        return true;
    }

    @Override
    public void recordBookingAttempt(Long userId) {
        int count = bookingAttempts.getOrDefault(userId, 0) + 1;
        bookingAttempts.put(userId, count);

        if (count > MAX_BOOKING_ATTEMPTS) {
            userBlockTime.put(userId, Instant.now());
            throw new ApiException("Too many booking attempts. User temporarily blocked.");
        }
    }

    @Override
    public void recordPaymentAttempt(Long userId) {
        int count = paymentAttempts.getOrDefault(userId, 0) + 1;
        paymentAttempts.put(userId, count);

        if (count > MAX_PAYMENT_ATTEMPTS) {
            userBlockTime.put(userId, Instant.now());
            throw new ApiException("Payment fraud suspected. User temporarily blocked.");
        }
    }

    @Override
    public boolean isPaymentSuspicious(Long userId, double amount) {
        // Rule: Very high value transactions after many attempts = suspicious
        return amount > 25000 && paymentAttempts.getOrDefault(userId, 0) >= 3;
    }

    @Override
    public void recordFraudEvent(Long userId, String reason) {
        userBlockTime.put(userId, Instant.now());
        System.out.println("⚠ FRAUD DETECTED: User " + userId + " → " + reason);
    }

    @Override
    public void resetDailyAttempts(Long userId) {
        bookingAttempts.remove(userId);
        paymentAttempts.remove(userId);
    }

    @Override
    public boolean isIpBlocked(String ip) {
        int count = ipAttempts.getOrDefault(ip, 0);
        return count > MAX_IP_ATTEMPTS;
    }
}
