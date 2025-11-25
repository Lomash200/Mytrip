package com.lomash.mytrip.service;

import com.lomash.mytrip.entity.Booking;
import com.lomash.mytrip.entity.FlightBooking;

public interface EmailEventsService {

    void sendWelcomeEmail(String email, String name);

    void sendHotelBookingConfirmation(Booking booking);

    void sendFlightBookingConfirmation(FlightBooking flightBooking);

    void sendBookingCancelled(String email, String reference);
}
