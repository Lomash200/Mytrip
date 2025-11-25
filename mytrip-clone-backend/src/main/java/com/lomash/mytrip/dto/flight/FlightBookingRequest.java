package com.lomash.mytrip.dto.flight;

import lombok.Data;

@Data
public class FlightBookingRequest {
    private Long flightId;
    private int passengers;         // number of seats requested
    private String travelDate;      // YYYY-MM-DD (should match flight departure date)
    // optionally passenger details, contact info, class (economy/business) etc.
}
