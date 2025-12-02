package com.lomash.mytrip.service;

import com.lomash.mytrip.dto.hotel.HotelRequest;
import com.lomash.mytrip.dto.hotel.HotelResponse;
import com.lomash.mytrip.entity.Hotel;
import com.lomash.mytrip.entity.Room;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface HotelService {
    HotelResponse createHotel(HotelRequest request);
    HotelResponse updateHotel(Long id, HotelRequest request);
    void deleteHotel(Long id);
    HotelResponse getHotelById(Long id);
    Page<HotelResponse> getAllHotels(int page, int size);
    void addHotelImage(Long hotelId, String imageUrl);
    List<Hotel> searchHotels(String city, String name, Double minPrice, Double maxPrice);
    Hotel getHotel(Long hotelId);
    List<Room> getRoomsForHotel(Long hotelId);
    boolean isRoomAvailable(Long roomId, LocalDate checkIn, LocalDate checkOut);

}
