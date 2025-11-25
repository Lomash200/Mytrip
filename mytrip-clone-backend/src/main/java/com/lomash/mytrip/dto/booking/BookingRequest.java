package com.lomash.mytrip.dto.booking;

import lombok.Data;

@Data
public class BookingRequest {
    private Long hotelId;
    private Long roomId;
    private String checkInDate;   // YYYY-MM-DD
    private String checkOutDate;  // YYYY-MM-DD
    private int guests;
}
