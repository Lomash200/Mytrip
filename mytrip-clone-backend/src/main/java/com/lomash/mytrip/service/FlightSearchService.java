package com.lomash.mytrip.service;

import com.lomash.mytrip.dto.search.FlightSearchRequest;
import com.lomash.mytrip.dto.search.FlightSearchResponse;

import java.util.List;

public interface FlightSearchService {
    List<FlightSearchResponse> searchFlights(FlightSearchRequest request);
}
