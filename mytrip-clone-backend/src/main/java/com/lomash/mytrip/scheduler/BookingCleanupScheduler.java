package com.lomash.mytrip.scheduler;

import com.lomash.mytrip.entity.Booking;
import com.lomash.mytrip.repository.BookingRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class BookingCleanupScheduler {

    private final BookingRepository bookingRepository;

    public BookingCleanupScheduler(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    // runs every 5 minutes
    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void cancelExpiredBookings() {

        Instant now = Instant.now();

        List<Booking> expired = bookingRepository
                .findByPaymentStatusAndReservationExpiresAtBefore(
                        "PENDING_PAYMENT",
                        now
                );

        if (expired.isEmpty()) return;

        for (Booking b : expired) {
            b.setPaymentStatus("CANCELLED");
        }

        bookingRepository.saveAll(expired);

        System.out.println("AUTO CANCELLED BOOKINGS = " + expired.size());
    }
}
