package com.lomash.mytrip.service;

import com.lomash.mytrip.dto.hotel.HotelRequest;
import com.lomash.mytrip.dto.hotel.HotelResponse;
import org.springframework.data.domain.Page;

public interface HotelService {
    HotelResponse createHotel(HotelRequest request);
    HotelResponse updateHotel(Long id, HotelRequest request);
    void deleteHotel(Long id);
    HotelResponse getHotelById(Long id);
    Page<HotelResponse> getAllHotels(int page, int size);
    void addHotelImage(Long hotelId, String imageUrl);

}
