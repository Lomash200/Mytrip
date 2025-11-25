package com.lomash.mytrip.dto.flight;

import lombok.Data;

@Data
public class FlightRequest {
    private String airline;
    private String flightNumber;
    private Long originId;
    private Long destinationId;
    private String departureTime;
    private String arrivalTime;
    private double price;
    private int seatsAvailable;
}
