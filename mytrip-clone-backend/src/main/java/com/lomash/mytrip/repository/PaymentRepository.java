package com.lomash.mytrip.repository;

import com.lomash.mytrip.entity.Booking;
import com.lomash.mytrip.entity.PaymentRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<PaymentRecord, Long> {
    Optional<PaymentRecord> findByPaymentId(String paymentId);
    Optional<PaymentRecord> findByOrderId(String orderId);
    Optional<PaymentRecord> findByBooking(Booking booking);
}
