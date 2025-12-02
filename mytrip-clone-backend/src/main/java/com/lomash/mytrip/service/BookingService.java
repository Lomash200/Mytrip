package com.lomash.mytrip.service;

import com.lomash.mytrip.dto.booking.BookingRequest;
import com.lomash.mytrip.dto.booking.BookingResponse;
import java.util.List;

public interface BookingService {
    BookingResponse createBooking(BookingRequest request, Long loggedUserId);

    BookingResponse createBooking(BookingRequest request);

    BookingResponse cancelBooking(Long bookingId);

    List<BookingResponse> getMyBookings();

    List<BookingResponse> getAllBookings();
}
