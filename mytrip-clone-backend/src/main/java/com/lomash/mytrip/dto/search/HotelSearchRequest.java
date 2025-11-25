package com.lomash.mytrip.dto.search;

import lombok.Data;

@Data
public class HotelSearchRequest {
    private Long locationId;
    private String checkIn;
    private String checkOut;
    private Integer guests;
    private int page = 0;
    private int size = 10;
}
