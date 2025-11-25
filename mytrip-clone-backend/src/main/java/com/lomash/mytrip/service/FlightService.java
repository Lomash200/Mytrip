package com.lomash.mytrip.service;

import com.lomash.mytrip.dto.flight.FlightRequest;
import com.lomash.mytrip.dto.flight.FlightResponse;

import java.util.List;

public interface FlightService {

    FlightResponse addFlight(FlightRequest request);

    FlightResponse updateFlight(Long id, FlightRequest request);

    void deleteFlight(Long id);

    List<FlightResponse> getAllFlights();
}
