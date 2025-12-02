package com.lomash.mytrip.scheduler;

import com.lomash.mytrip.entity.Booking;
import com.lomash.mytrip.repository.BookingRepository;
import com.lomash.mytrip.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationScheduler {

    private final BookingRepository bookingRepository;
    private final EmailService emailService;

    @Scheduled(cron = "0 0 9 * * ?") // Daily at 9 AM
    public void sendReminderEmails() {
        LocalDate tomorrow = LocalDate.now().plus(1, ChronoUnit.DAYS);

        List<Booking> tomorrowBookings = bookingRepository.findAll()
                .stream()
                .filter(booking ->
                        booking.getCheckInDate() != null &&
                                booking.getCheckInDate().equals(tomorrow) &&
                                "PAID".equals(booking.getPaymentStatus())
                )
                .toList();

        for (Booking booking : tomorrowBookings) {
            try {
                emailService.sendEmail(
                        booking.getUser().getEmail(),
                        "Reminder: Check-in Tomorrow!",
                        String.format(
                                "Hi %s,<br><br>Your check-in at %s is tomorrow (%s).<br><br>Enjoy your stay!",
                                booking.getUser().getFirstName(),
                                booking.getHotel().getName(),
                                booking.getCheckInDate()
                        )
                );
            } catch (Exception e) {
                System.out.println("Failed to send reminder email: " + e.getMessage());
            }
        }
    }
}