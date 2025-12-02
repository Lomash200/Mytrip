package com.lomash.mytrip.mapper;

import com.lomash.mytrip.dto.booking.BookingResponse;
import com.lomash.mytrip.entity.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

    public BookingResponse toResponse(Booking booking) {
        if (booking == null) return null;

        return BookingResponse.builder()
                .bookingId(booking.getId())
                .referenceCode(booking.getReferenceCode())
                .hotelId(booking.getHotel() != null ? booking.getHotel().getId() : null)
                .roomId(booking.getRoom() != null ? booking.getRoom().getId() : null)
                .checkInDate(booking.getCheckInDate())
                .checkOutDate(booking.getCheckOutDate())
                .guests(booking.getGuests())
                .finalAmount(booking.getTotalAmount())
                .paymentStatus(booking.getPaymentStatus())
                .message("Booking details")
                .build();
    }
}