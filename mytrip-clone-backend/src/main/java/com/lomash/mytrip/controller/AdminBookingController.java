package com.lomash.mytrip.controller;

import com.lomash.mytrip.dto.booking.BookingResponse;
import com.lomash.mytrip.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/bookings")
public class AdminBookingController {

    private final BookingService bookingService;

    public AdminBookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // POST /api/admin/bookings/{id}/cancel
    @PostMapping("/{id}/cancel")
    public ResponseEntity<BookingResponse> cancelBooking(@PathVariable("id") Long id) {
        BookingResponse resp = bookingService.cancelBooking(id);
        if (resp == null || "FAILED".equalsIgnoreCase(resp.getPaymentStatus())) {
            return ResponseEntity.badRequest().body(resp);
        }
        return ResponseEntity.ok(resp);
    }
}
