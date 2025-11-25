package com.lomash.mytrip.controller;

import com.lomash.mytrip.dto.booking.BookingResponse;
import com.lomash.mytrip.service.BookingService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/bookings")
@PreAuthorize("hasRole('ADMIN')")
public class AdminBookingController {

    private final BookingService bookingService;

    public AdminBookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public List<BookingResponse> allBookings() {
        return bookingService.getAllBookings();
    }
}
