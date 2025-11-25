package com.lomash.mytrip.dto.flight;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightBookingResponse {
    private Long id;
    private String pnr;
    private String status;
    private double totalAmount;
    private String airline;
    private String flightNumber;
    private String origin;
    private String destination;
    private String departureTime;
    private int passengers;
    private String travelDate;
}
