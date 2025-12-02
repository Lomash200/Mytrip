package com.lomash.mytrip.scheduler;

import com.lomash.mytrip.entity.Booking;
import com.lomash.mytrip.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BookingCleanupJob {

    private final BookingRepository bookingRepository;

    @Scheduled(fixedRate = 300000) // Every 5 minutes
    public void cleanupExpiredBookings() {
        Instant cutoffTime = Instant.now().minus(15, ChronoUnit.MINUTES);

        List<Booking> expiredBookings = bookingRepository.findAll()
                .stream()
                .filter(booking ->
                        "PENDING_PAYMENT".equals(booking.getPaymentStatus()) &&
                                booking.getReservationExpiresAt() != null &&
                                booking.getReservationExpiresAt().isBefore(cutoffTime)
                )
                .toList();

        if (!expiredBookings.isEmpty()) {
            expiredBookings.forEach(booking -> {
                booking.setPaymentStatus("EXPIRED");
                bookingRepository.save(booking);
            });

            System.out.println("Cleaned up " + expiredBookings.size() + " expired bookings");
        }
    }
}