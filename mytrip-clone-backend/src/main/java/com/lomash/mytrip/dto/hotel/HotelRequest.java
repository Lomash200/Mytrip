package com.lomash.mytrip.dto.hotel;

import lombok.Data;

@Data
public class HotelRequest {
    private String name;
    private String description;
    private Long locationId;
    private String address;
    private double rating; // optional
}
