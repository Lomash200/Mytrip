package com.lomash.mytrip.dto.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class HotelSearchResponse {

    private Long hotelId;
    private String hotelName;
    private String city;
    private String address;

    private double rating;
    private int starCategory;

    private double startingPrice;
    private boolean available;

    private String imageUrl;
}
