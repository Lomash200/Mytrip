package com.lomash.mytrip.controller;

import com.lomash.mytrip.dto.flight.FlightBookingResponse;
import com.lomash.mytrip.service.FlightBookingService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/flights/bookings")
@PreAuthorize("hasRole('ADMIN')")
public class AdminFlightBookingController {

    private final FlightBookingService service;

    public AdminFlightBookingController(FlightBookingService service) {
        this.service = service;
    }

    @GetMapping
    public List<FlightBookingResponse> allBookings() {
        return service.getAllBookings();
    }
}
