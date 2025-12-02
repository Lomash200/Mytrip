package com.lomash.mytrip.repository;

import com.lomash.mytrip.entity.PaymentRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRecordRepository extends JpaRepository<PaymentRecord, Long> {

    // find latest payment record for a booking (most recent)
    Optional<PaymentRecord> findTopByBookingIdOrderByIdDesc(Long bookingId);


}
