package com.lomash.mytrip.controller;

import com.lomash.mytrip.dto.search.HotelSearchRequest;
import com.lomash.mytrip.dto.search.HotelSearchResponse;
import com.lomash.mytrip.service.SearchService;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping("/hotels")
    public Page<HotelSearchResponse> searchHotels(@RequestBody HotelSearchRequest request) {
        return searchService.searchHotels(request);
    }
}
