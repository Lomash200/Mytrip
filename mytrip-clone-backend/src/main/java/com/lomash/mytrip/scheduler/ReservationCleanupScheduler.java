package com.lomash.mytrip.scheduler;

import com.lomash.mytrip.entity.Booking;
import com.lomash.mytrip.repository.BookingRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class ReservationCleanupScheduler {

    private final BookingRepository bookingRepository;

    public ReservationCleanupScheduler(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    // Run every 1 minute
    @Scheduled(fixedDelay = 60_000)
    public void cleanupExpiredBookings() {

        Instant now = Instant.now();

        // fetch all expired "PENDING_PAYMENT" bookings
        List<Booking> expired = bookingRepository.findAll()
                .stream()
                .filter(b ->
                        "PENDING_PAYMENT".equals(b.getPaymentStatus()) &&
                                b.getReservationExpiresAt() != null &&
                                b.getReservationExpiresAt().isBefore(now)
                )
                .toList();

        if (expired.isEmpty()) return;

        for (Booking b : expired) {
            b.setPaymentStatus("EXPIRED");
            bookingRepository.save(b);
        }

        System.out.println("Expired bookings cleaned: " + expired.size());
    }
}
