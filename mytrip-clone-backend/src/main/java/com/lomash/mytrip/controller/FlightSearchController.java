package com.lomash.mytrip.controller;

import com.lomash.mytrip.dto.search.FlightSearchRequest;
import com.lomash.mytrip.dto.search.FlightSearchResponse;
import com.lomash.mytrip.service.FlightSearchService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search/flights")
public class FlightSearchController {

    private final FlightSearchService service;

    public FlightSearchController(FlightSearchService service) {
        this.service = service;
    }

    @PostMapping
    public List<FlightSearchResponse> search(@RequestBody FlightSearchRequest request) {
        return service.searchFlights(request);
    }
}
