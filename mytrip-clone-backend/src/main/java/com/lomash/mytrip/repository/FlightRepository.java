package com.lomash.mytrip.repository;

import com.lomash.mytrip.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    List<Flight> findByOriginIdAndDestinationId(Long originId, Long destinationId);

    List<Flight> findByOriginIdAndDestinationIdAndDepartureTimeStartingWith(
            Long originId, Long destinationId, String date
    );
}
