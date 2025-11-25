package com.lomash.mytrip.repository;

import com.lomash.mytrip.entity.FlightBooking;
import com.lomash.mytrip.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FlightBookingRepository extends JpaRepository<FlightBooking, Long> {
    List<FlightBooking> findByUser(User user);
    Optional<FlightBooking> findByPnr(String pnr);
}
