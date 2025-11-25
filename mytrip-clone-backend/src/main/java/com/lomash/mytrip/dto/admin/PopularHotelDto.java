package com.lomash.mytrip.dto.admin;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PopularHotelDto {
    private Long hotelId;
    private String hotelName;
    private long totalBookings;
}
