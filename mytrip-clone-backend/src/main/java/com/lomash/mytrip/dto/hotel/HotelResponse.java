package com.lomash.mytrip.dto.hotel;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelResponse {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String locationName;
    private double rating;
}
