package com.lomash.mytrip.dto.hotel;

import com.lomash.mytrip.dto.room.RoomDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class HotelDetailsResponse {

    private Long hotelId;
    private String hotelName;
    private String city;
    private String address;
    private double rating;
    private int starCategory;
    private String description;
    private String imageUrl;

    private List<String> amenities;

    private List<RoomDetailsDto> rooms;  // all rooms with availability & price
}
