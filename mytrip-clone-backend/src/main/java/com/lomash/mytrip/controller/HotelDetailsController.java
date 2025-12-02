package com.lomash.mytrip.controller;

import com.lomash.mytrip.dto.hotel.HotelDetailsResponse;
import com.lomash.mytrip.service.HotelDetailsService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/hotel")
public class HotelDetailsController {

    private final HotelDetailsService detailsService;

    public HotelDetailsController(HotelDetailsService detailsService) {
        this.detailsService = detailsService;
    }

    @GetMapping("/{id}")
    public HotelDetailsResponse getDetails(
            @PathVariable Long id,
            @RequestParam LocalDate checkIn,
            @RequestParam LocalDate checkOut
    ) {
        return detailsService.getHotelDetails(id, checkIn, checkOut);
    }
}
