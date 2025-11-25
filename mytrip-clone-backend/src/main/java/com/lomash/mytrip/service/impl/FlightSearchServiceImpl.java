package com.lomash.mytrip.service.impl;

import com.lomash.mytrip.dto.search.FlightSearchRequest;
import com.lomash.mytrip.dto.search.FlightSearchResponse;
import com.lomash.mytrip.entity.Flight;
import com.lomash.mytrip.repository.FlightRepository;
import com.lomash.mytrip.repository.LocationRepository;
import com.lomash.mytrip.service.FlightSearchService;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class FlightSearchServiceImpl implements FlightSearchService {

    private final FlightRepository flightRepository;
    private final LocationRepository locationRepository;

    public FlightSearchServiceImpl(FlightRepository flightRepository,
                                   LocationRepository locationRepository) {
        this.flightRepository = flightRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public List<FlightSearchResponse> searchFlights(FlightSearchRequest req) {

        List<Flight> flights =
                flightRepository.findByOriginIdAndDestinationIdAndDepartureTimeStartingWith(
                        req.getOriginId(), req.getDestinationId(), req.getDate()
                );

        // filters
        flights = flights.stream()
                .filter(f -> req.getMinPrice() == null || f.getPrice() >= req.getMinPrice())
                .filter(f -> req.getMaxPrice() == null || f.getPrice() <= req.getMaxPrice())
                .toList();

        // sorting
        if ("price".equalsIgnoreCase(req.getSortBy())) {
            flights = flights.stream()
                    .sorted(req.getSortOrder().equalsIgnoreCase("DESC")
                            ? Comparator.comparingDouble(Flight::getPrice).reversed()
                            : Comparator.comparingDouble(Flight::getPrice))
                    .toList();
        }

        return flights.stream()
                .map(f -> FlightSearchResponse.builder()
                        .id(f.getId())
                        .airline(f.getAirline())
                        .flightNumber(f.getFlightNumber())
                        .origin(f.getOrigin().getName())
                        .destination(f.getDestination().getName())
                        .departureTime(f.getDepartureTime())
                        .arrivalTime(f.getArrivalTime())
                        .price(f.getPrice())
                        .seatsAvailable(f.getSeatsAvailable())
                        .build())
                .toList();
    }
}
