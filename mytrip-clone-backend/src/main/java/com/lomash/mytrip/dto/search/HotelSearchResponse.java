package com.lomash.mytrip.dto.search;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelSearchResponse {
    private Long id;
    private String name;
    private String locationName;
    private double rating;
    private double priceFrom;
}
