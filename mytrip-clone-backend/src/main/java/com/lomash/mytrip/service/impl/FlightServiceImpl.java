package com.lomash.mytrip.service.impl;

import com.lomash.mytrip.dto.flight.FlightRequest;
import com.lomash.mytrip.dto.flight.FlightResponse;
import com.lomash.mytrip.entity.Flight;
import com.lomash.mytrip.entity.Location;
import com.lomash.mytrip.exception.ApiException;
import com.lomash.mytrip.repository.FlightRepository;
import com.lomash.mytrip.repository.LocationRepository;
import com.lomash.mytrip.service.FlightService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final LocationRepository locationRepository;

    public FlightServiceImpl(FlightRepository flightRepository, LocationRepository locationRepository) {
        this.flightRepository = flightRepository;
        this.locationRepository = locationRepository;
    }

    private FlightResponse map(Flight f) {
        return FlightResponse.builder()
                .id(f.getId())
                .airline(f.getAirline())
                .flightNumber(f.getFlightNumber())
                .origin(f.getOrigin().getName())
                .destination(f.getDestination().getName())
                .departureTime(f.getDepartureTime())
                .arrivalTime(f.getArrivalTime())
                .price(f.getPrice())
                .seatsAvailable(f.getSeatsAvailable())
                .build();
    }

    @Override
    public FlightResponse addFlight(FlightRequest request) {

        Location origin = locationRepository.findById(request.getOriginId())
                .orElseThrow(() -> new ApiException("Invalid origin"));

        Location destination = locationRepository.findById(request.getDestinationId())
                .orElseThrow(() -> new ApiException("Invalid destination"));

        Flight flight = Flight.builder()
                .airline(request.getAirline())
                .flightNumber(request.getFlightNumber())
                .origin(origin)
                .destination(destination)
                .departureTime(request.getDepartureTime())
                .arrivalTime(request.getArrivalTime())
                .price(request.getPrice())
                .seatsAvailable(request.getSeatsAvailable())
                .build();

        return map(flightRepository.save(flight));
    }

    @Override
    public FlightResponse updateFlight(Long id, FlightRequest request) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ApiException("Flight not found"));

        if (request.getAirline() != null) flight.setAirline(request.getAirline());
        if (request.getFlightNumber() != null) flight.setFlightNumber(request.getFlightNumber());

        if (request.getOriginId() != null) {
            flight.setOrigin(locationRepository.findById(request.getOriginId())
                    .orElseThrow(() -> new ApiException("Invalid origin")));
        }

        if (request.getDestinationId() != null) {
            flight.setDestination(locationRepository.findById(request.getDestinationId())
                    .orElseThrow(() -> new ApiException("Invalid destination")));
        }

        if (request.getDepartureTime() != null) flight.setDepartureTime(request.getDepartureTime());
        if (request.getArrivalTime() != null) flight.setArrivalTime(request.getArrivalTime());
        if (request.getPrice() > 0) flight.setPrice(request.getPrice());
        if (request.getSeatsAvailable() >= 0) flight.setSeatsAvailable(request.getSeatsAvailable());

        return map(flightRepository.save(flight));
    }

    @Override
    public void deleteFlight(Long id) {
        if (!flightRepository.existsById(id))
            throw new ApiException("Flight not found");
        flightRepository.deleteById(id);
    }

    @Override
    public List<FlightResponse> getAllFlights() {
        return flightRepository.findAll().stream().map(this::map).toList();
    }
}
