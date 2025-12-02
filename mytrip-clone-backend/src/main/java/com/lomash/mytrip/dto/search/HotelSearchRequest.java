package com.lomash.mytrip.dto.search;

import lombok.Data;
import java.time.LocalDate;

@Data
public class HotelSearchRequest {
    private String city;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int guests;

    private Double minPrice;
    private Double maxPrice;
    private String sortBy; // price_low, price_high, rating
    private int page = 0;
    private int size = 10;
}
