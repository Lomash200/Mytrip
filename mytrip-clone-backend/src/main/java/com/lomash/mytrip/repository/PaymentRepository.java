package com.lomash.mytrip.repository;

import com.lomash.mytrip.entity.Booking;
import com.lomash.mytrip.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByPaymentId(String paymentId);
    Optional<Payment> findByOrderId(String orderId);
    Optional<Payment> findByBooking(Booking booking);
}
