package com.lomash.mytrip.service;

public interface FraudCheckService {

    boolean isUserBlocked(Long userId);

    void recordBookingAttempt(Long userId);

    void recordPaymentAttempt(Long userId);

    void recordFraudEvent(Long userId, String reason);

    void resetDailyAttempts(Long userId);

    boolean isPaymentSuspicious(Long userId, double amount);

    boolean isIpBlocked(String ip);
}
