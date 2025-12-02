package com.lomash.mytrip.controller;

import com.lomash.mytrip.entity.Hotel;
import com.lomash.mytrip.entity.Room;
import com.lomash.mytrip.service.HotelService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    // Search hotels by city/name and optional price range
    @GetMapping
    public List<Hotel> searchHotels(@RequestParam(required = false) String city,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false) Double minPrice,
                                    @RequestParam(required = false) Double maxPrice) {
        return hotelService.searchHotels(city, name, minPrice, maxPrice);
    }

    @GetMapping("/{hotelId}")
    public Hotel getHotel(@PathVariable Long hotelId) {
        return hotelService.getHotel(hotelId);
    }

    @GetMapping("/{hotelId}/rooms")
    public List<Room> getRooms(@PathVariable Long hotelId) {
        return hotelService.getRoomsForHotel(hotelId);
    }

    // Check room availability
    @GetMapping("/rooms/{roomId}/availability")
    public boolean checkRoomAvailability(@PathVariable Long roomId,
                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut) {
        return hotelService.isRoomAvailable(roomId, checkIn, checkOut);
    }
}
