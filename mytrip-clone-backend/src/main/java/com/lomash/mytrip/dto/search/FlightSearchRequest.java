package com.lomash.mytrip.dto.search;

import lombok.Data;

@Data
public class FlightSearchRequest {

    private Long originId;
    private Long destinationId;

    private String date; // YYYY-MM-DD

    private Double minPrice;
    private Double maxPrice;

    private String sortBy; // price, departureTime
    private String sortOrder; // ASC, DESC
}
