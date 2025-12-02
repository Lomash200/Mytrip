package com.lomash.mytrip.controller;

import com.lomash.mytrip.dto.booking.BookingRequest;
import com.lomash.mytrip.dto.booking.BookingResponse;
import com.lomash.mytrip.service.AuthService;
import com.lomash.mytrip.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final AuthService authService;

    public BookingController(BookingService bookingService, AuthService authService) {
        this.bookingService = bookingService;
        this.authService = authService;
    }

    // CREATE BOOKING
    @PostMapping("/create")
    public ResponseEntity<BookingResponse> createBooking(@RequestBody BookingRequest request) {

        Long userId = null;
        try {
            if (authService.getLoggedUser() != null) {
                userId = authService.getLoggedUser().getId();
            }
        } catch (Exception ignored) {}

        BookingResponse resp = bookingService.createBooking(request, userId);

        if (resp.getBookingId() == null) {
            return ResponseEntity.badRequest().body(resp);
        }

        return ResponseEntity.ok(resp);
    }

    // USER BOOKINGS
    @GetMapping("/me")
    public ResponseEntity<?> getMyBookings() {
        return ResponseEntity.ok(bookingService.getMyBookings());
    }

    // ADMIN ALL BOOKINGS
    @GetMapping("/all")
    public ResponseEntity<?> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }
}
