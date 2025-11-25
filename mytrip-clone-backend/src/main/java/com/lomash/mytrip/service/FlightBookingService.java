package com.lomash.mytrip.service;

import com.lomash.mytrip.dto.flight.FlightBookingRequest;
import com.lomash.mytrip.dto.flight.FlightBookingResponse;

import java.util.List;

public interface FlightBookingService {
    FlightBookingResponse createBooking(FlightBookingRequest request); // creates PENDING booking and locks seats
    FlightBookingResponse confirmBookingByPnr(String pnr);             // called after successful payment
    FlightBookingResponse cancelBooking(String pnr);                  // cancels and releases seats
    List<FlightBookingResponse> getMyBookings();
    List<FlightBookingResponse> getAllBookings(); // admin
}
