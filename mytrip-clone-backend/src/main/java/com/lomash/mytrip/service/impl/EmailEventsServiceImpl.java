package com.lomash.mytrip.service.impl;

import com.lomash.mytrip.entity.Booking;
import com.lomash.mytrip.entity.FlightBooking;
import com.lomash.mytrip.service.EmailEventsService;
import com.lomash.mytrip.service.EmailService;
import com.lomash.mytrip.util.EmailTemplateProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailEventsServiceImpl implements EmailEventsService {

    private final EmailService emailService;

    @Override
    public void sendWelcomeEmail(String email, String name) {

        String html = EmailTemplateProcessor.processTemplate(
                "welcome-email.html",
                Map.of("name", name)
        );

        emailService.sendEmail(email, "Welcome to MyTrip", html);
    }

    @Override
    public void sendHotelBookingConfirmation(Booking booking) {

        String html = EmailTemplateProcessor.processTemplate(
                "booking-confirmation.html",
                Map.of(
                        "name", booking.getUser().getFirstName(),
                        "reference", booking.getReferenceCode(),
                        "title", booking.getHotel().getName(),
                        "date", booking.getCheckInDate() + " - " + booking.getCheckOutDate(),
                        "amount", String.valueOf(booking.getTotalAmount())
                )
        );

        emailService.sendEmail(
                booking.getUser().getEmail(),
                "Hotel Booking Confirmed",
                html
        );
    }

    @Override
    public void sendFlightBookingConfirmation(FlightBooking fl) {

        String html = EmailTemplateProcessor.processTemplate(
                "booking-confirmation.html",
                Map.of(
                        "name", fl.getUser().getFirstName(),
                        "reference", fl.getPnr(),
                        "title", fl.getFlight().getAirline() + " " + fl.getFlight().getFlightNumber(),
                        "date", fl.getTravelDate(),
                        "amount", String.valueOf(fl.getTotalAmount())
                )
        );

        emailService.sendEmail(
                fl.getUser().getEmail(),
                "Flight Ticket Confirmed",
                html
        );
    }

    @Override
    public void sendBookingCancelled(String email, String reference) {

        String html = EmailTemplateProcessor.processTemplate(
                "booking-cancel.html",
                Map.of(
                        "name", "User",
                        "reference", reference
                )
        );

        emailService.sendEmail(email, "Booking Cancelled", html);
    }
}
