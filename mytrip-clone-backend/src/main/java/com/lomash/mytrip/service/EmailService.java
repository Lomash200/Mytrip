package com.lomash.mytrip.service;

public interface EmailService {

    void sendEmail(String to, String subject, String content);

    void sendBookingConfirmation(
            String to,
            String bookingRef,
            String hotelName,
            String roomType,
            String checkIn,
            String checkOut,
            double amount
    );
}
