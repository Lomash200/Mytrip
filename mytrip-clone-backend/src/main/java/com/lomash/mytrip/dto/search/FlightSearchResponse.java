package com.lomash.mytrip.dto.search;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightSearchResponse {

    private Long id;
    private String airline;
    private String flightNumber;
    private String origin;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private double price;
    private int seatsAvailable;
}
