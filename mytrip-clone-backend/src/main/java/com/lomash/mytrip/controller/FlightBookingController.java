package com.lomash.mytrip.controller;

import com.lomash.mytrip.dto.flight.FlightBookingRequest;
import com.lomash.mytrip.dto.flight.FlightBookingResponse;
import com.lomash.mytrip.service.FlightBookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights/bookings")
public class FlightBookingController {

    private final FlightBookingService service;

    public FlightBookingController(FlightBookingService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<FlightBookingResponse> create(@RequestBody FlightBookingRequest request) {
        return ResponseEntity.ok(service.createBooking(request));
    }

    // This endpoint could be called by payment webhook or frontend after payment
    @PostMapping("/confirm/{pnr}")
    public ResponseEntity<FlightBookingResponse> confirm(@PathVariable String pnr) {
        return ResponseEntity.ok(service.confirmBookingByPnr(pnr));
    }

    @PutMapping("/cancel/{pnr}")
    public ResponseEntity<FlightBookingResponse> cancel(@PathVariable String pnr) {
        return ResponseEntity.ok(service.cancelBooking(pnr));
    }

    @GetMapping
    public List<FlightBookingResponse> myBookings() {
        return service.getMyBookings();
    }
}
