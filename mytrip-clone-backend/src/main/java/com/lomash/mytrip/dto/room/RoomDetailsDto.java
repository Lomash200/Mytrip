package com.lomash.mytrip.dto.room;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomDetailsDto {

    private Long roomId;
    private String roomType;
    private double pricePerNight;
    private int capacity;
    private boolean available;
    private String roomImage;
}
