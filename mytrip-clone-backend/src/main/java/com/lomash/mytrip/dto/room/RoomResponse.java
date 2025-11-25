package com.lomash.mytrip.dto.room;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomResponse {
    private Long id;
    private String roomType;
    private double pricePerNight;
    private int maxGuests;
    private int availabilityCount;
}
