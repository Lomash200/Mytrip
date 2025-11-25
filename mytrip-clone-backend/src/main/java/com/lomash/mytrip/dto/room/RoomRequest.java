package com.lomash.mytrip.dto.room;

import lombok.Data;

@Data
public class RoomRequest {
    private String roomType;        // SINGLE, DOUBLE, SUITE
    private double pricePerNight;
    private int maxGuests;
    private int availabilityCount;
}
