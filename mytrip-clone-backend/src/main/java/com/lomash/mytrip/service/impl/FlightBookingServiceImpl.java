package com.lomash.mytrip.service.impl;

import com.lomash.mytrip.dto.flight.FlightBookingRequest;
import com.lomash.mytrip.dto.flight.FlightBookingResponse;
import com.lomash.mytrip.entity.Flight;
import com.lomash.mytrip.entity.FlightBooking;
import com.lomash.mytrip.entity.User;
import com.lomash.mytrip.entity.enums.FlightBookingStatus;
import com.lomash.mytrip.exception.ApiException;
import com.lomash.mytrip.repository.FlightBookingRepository;
import com.lomash.mytrip.repository.FlightRepository;
import com.lomash.mytrip.repository.UserRepository;
import com.lomash.mytrip.service.FlightBookingService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class FlightBookingServiceImpl implements FlightBookingService {

    private final FlightBookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;

    public FlightBookingServiceImpl(FlightBookingRepository bookingRepository,
                                    FlightRepository flightRepository,
                                    UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userRepository.findByUsername(username)
                .or(() -> userRepository.findByEmail(username))
                .orElseThrow(() -> new ApiException("User not found"));
    }

    private FlightBookingResponse map(FlightBooking b) {
        Flight f = b.getFlight();
        return FlightBookingResponse.builder()
                .id(b.getId())
                .pnr(b.getPnr())
                .status(b.getStatus().name())
                .totalAmount(b.getTotalAmount())
                .airline(f.getAirline())
                .flightNumber(f.getFlightNumber())
                .origin(f.getOrigin().getName())
                .destination(f.getDestination().getName())
                .departureTime(f.getDepartureTime())
                .passengers(b.getPassengers())
                .travelDate(b.getTravelDate())
                .build();
    }

    private String generatePnr() {
        return "PNR" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    // Create PENDING booking and decrease seats (seat lock)
    @Override
    @Transactional
    public FlightBookingResponse createBooking(FlightBookingRequest request) {
        Flight flight = flightRepository.findById(request.getFlightId())
                .orElseThrow(() -> new ApiException("Flight not found"));

        if (request.getPassengers() <= 0) throw new ApiException("Invalid passenger count");

        if (flight.getSeatsAvailable() < request.getPassengers()) {
            throw new ApiException("Not enough seats available");
        }

        // lock seats (decrease availability)
        flight.setSeatsAvailable(flight.getSeatsAvailable() - request.getPassengers());
        flightRepository.save(flight);

        User user = getCurrentUser();

        double total = flight.getPrice() * request.getPassengers(); // simple price calc

        FlightBooking booking = FlightBooking.builder()
                .pnr(generatePnr())
                .travelDate(request.getTravelDate())
                .passengers(request.getPassengers())
                .totalAmount(total)
                .status(FlightBookingStatus.PENDING)
                .flight(flight)
                .user(user)
                .build();

        booking = bookingRepository.save(booking);

        return map(booking);
    }

    // Confirm booking after successful payment (idempotent)
    @Override
    @Transactional
    public FlightBookingResponse confirmBookingByPnr(String pnr) {
        FlightBooking booking = bookingRepository.findByPnr(pnr)
                .orElseThrow(() -> new ApiException("Booking not found"));

        if (booking.getStatus() == FlightBookingStatus.CONFIRMED) {
            return map(booking); // already confirmed
        }

        if (booking.getStatus() == FlightBookingStatus.CANCELLED) {
            throw new ApiException("Booking is cancelled");
        }

        booking.setStatus(FlightBookingStatus.CONFIRMED);
        booking = bookingRepository.save(booking);

        // you may create Payment entity record here or link existing payment

        return map(booking);
    }

    // Cancel booking and restore seats (support refund logic separately)
    @Override
    @Transactional
    public FlightBookingResponse cancelBooking(String pnr) {
        FlightBooking booking = bookingRepository.findByPnr(pnr)
                .orElseThrow(() -> new ApiException("Booking not found"));

        if (booking.getStatus() == FlightBookingStatus.CANCELLED) {
            throw new ApiException("Already cancelled");
        }

        // restore seats
        Flight f = booking.getFlight();
        f.setSeatsAvailable(f.getSeatsAvailable() + booking.getPassengers());
        flightRepository.save(f);

        booking.setStatus(FlightBookingStatus.CANCELLED);
        booking = bookingRepository.save(booking);

        // optionally trigger refund workflow if booking was CONFIRMED

        return map(booking);
    }

    @Override
    public List<FlightBookingResponse> getMyBookings() {
        User user = getCurrentUser();
        return bookingRepository.findByUser(user).stream().map(this::map).toList();
    }

    @Override
    public List<FlightBookingResponse> getAllBookings() {
        return bookingRepository.findAll().stream().map(this::map).toList();
    }
}
