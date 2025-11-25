package com.lomash.mytrip.dto.booking;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {
    private Long id;
    private String referenceCode;
    private String status;
    private double totalAmount;
    private String hotelName;
    private String roomType;
    private String checkInDate;
    private String checkOutDate;
}
