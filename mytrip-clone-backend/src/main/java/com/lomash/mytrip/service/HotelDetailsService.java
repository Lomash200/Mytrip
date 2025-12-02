package com.lomash.mytrip.service;

import com.lomash.mytrip.dto.hotel.HotelDetailsResponse;

import java.time.LocalDate;

public interface HotelDetailsService {

    HotelDetailsResponse getHotelDetails(Long hotelId, LocalDate checkIn, LocalDate checkOut);
}
