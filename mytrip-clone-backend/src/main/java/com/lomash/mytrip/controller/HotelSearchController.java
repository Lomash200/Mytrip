package com.lomash.mytrip.controller;

import com.lomash.mytrip.dto.search.HotelSearchRequest;
import com.lomash.mytrip.dto.search.HotelSearchResponse;
import com.lomash.mytrip.service.HotelSearchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelSearchController {

    private final HotelSearchService searchService;

    public HotelSearchController(HotelSearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping("/search")
    public List<HotelSearchResponse> search(@RequestBody HotelSearchRequest req) {
        return searchService.searchHotels(req);
    }
}
