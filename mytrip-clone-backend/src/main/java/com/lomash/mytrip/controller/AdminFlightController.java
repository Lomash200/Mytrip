package com.lomash.mytrip.controller;

import com.lomash.mytrip.dto.flight.FlightRequest;
import com.lomash.mytrip.dto.flight.FlightResponse;
import com.lomash.mytrip.service.FlightService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/flights")
@PreAuthorize("hasRole('ADMIN')")
public class AdminFlightController {

    private final FlightService service;

    public AdminFlightController(FlightService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<FlightResponse> add(@RequestBody FlightRequest req) {
        return ResponseEntity.ok(service.addFlight(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightResponse> update(
            @PathVariable Long id,
            @RequestBody FlightRequest req) {
        return ResponseEntity.ok(service.updateFlight(id, req));
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteFlight(id);
        return "Deleted";
    }

    @GetMapping
    public List<FlightResponse> getAll() {
        return service.getAllFlights();
    }
}
