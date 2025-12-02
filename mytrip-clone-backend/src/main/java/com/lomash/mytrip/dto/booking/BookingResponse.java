package com.lomash.mytrip.dto.booking;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder  // ✅ ADD THIS ANNOTATION
public class BookingResponse {

    private Long bookingId;
    private String referenceCode;

    private Long hotelId;
    private Long roomId;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    private int guests;

    // price breakdown
    private double baseAmount;
    private double gstAmount;
    private double serviceFee;
    private double convenienceFee;
    private double finalAmount;

    private String paymentStatus;
    private String message;

    // -----------------------------
    // SUCCESS for creation
    // -----------------------------
    public static BookingResponse success(
            Long bookingId,
            String referenceCode,
            Long hotelId,
            Long roomId,
            LocalDate checkInDate,
            LocalDate checkOutDate,
            int guests,
            double baseAmount,
            double gstAmount,
            double serviceFee,
            double convenienceFee,
            double finalAmount,
            String message
    ) {
        return BookingResponse.builder()
                .bookingId(bookingId)
                .referenceCode(referenceCode)
                .hotelId(hotelId)
                .roomId(roomId)
                .checkInDate(checkInDate)
                .checkOutDate(checkOutDate)
                .guests(guests)
                .baseAmount(baseAmount)
                .gstAmount(gstAmount)
                .serviceFee(serviceFee)
                .convenienceFee(convenienceFee)
                .finalAmount(finalAmount)
                .paymentStatus("PENDING_PAYMENT")
                .message(message)
                .build();
    }

    // -----------------------------
    // FAILED response
    // -----------------------------
    public static BookingResponse failed(String message) {
        return BookingResponse.builder()
                .bookingId(null)
                .referenceCode(null)
                .hotelId(null)
                .roomId(null)
                .checkInDate(null)
                .checkOutDate(null)
                .guests(0)
                .baseAmount(0)
                .gstAmount(0)
                .serviceFee(0)
                .convenienceFee(0)
                .finalAmount(0)
                .paymentStatus("FAILED")
                .message(message)
                .build();
    }

    // -----------------------------
    // SUCCESS for list/bookings page
    // -----------------------------
    public static BookingResponse successList(
            Long bookingId,
            String referenceCode,
            Long hotelId,
            Long roomId,
            LocalDate checkInDate,
            LocalDate checkOutDate,
            int guests,
            double finalAmount,
            String paymentStatus
    ) {
        return BookingResponse.builder()
                .bookingId(bookingId)
                .referenceCode(referenceCode)
                .hotelId(hotelId)
                .roomId(roomId)
                .checkInDate(checkInDate)
                .checkOutDate(checkOutDate)
                .guests(guests)
                .baseAmount(0)
                .gstAmount(0)
                .serviceFee(0)
                .convenienceFee(0)
                .finalAmount(finalAmount)
                .paymentStatus(paymentStatus)
                .message("OK")
                .build();
    }
}