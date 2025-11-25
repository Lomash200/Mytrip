package com.lomash.mytrip.controller;

import com.lomash.mytrip.dto.booking.BookingRequest;
import com.lomash.mytrip.dto.booking.BookingResponse;
import com.lomash.mytrip.exception.ApiException;
import com.lomash.mytrip.service.AuthService;
import com.lomash.mytrip.service.BookingService;
import com.lomash.mytrip.service.FraudCheckService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final FraudCheckService fraudCheckService;
    private final AuthService authService;

    public BookingController(
            BookingService bookingService,
            FraudCheckService fraudCheckService,
            AuthService authService
    ) {
        this.bookingService = bookingService;
        this.fraudCheckService = fraudCheckService;
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<BookingResponse> create(
            @RequestBody BookingRequest request,
            HttpServletRequest httpRequest
    ) {

        Long userId = authService.getLoggedUser().getId();

        // 🔥 IP FRAUD CHECK
        String ip = httpRequest.getRemoteAddr();
        if (fraudCheckService.isIpBlocked(ip)) {
            throw new ApiException("Too many requests detected from your network. Please try later.");
        }

        // 🔥 USER FRAUD CHECK — Too many booking attempts
        fraudCheckService.recordBookingAttempt(userId);

        if (fraudCheckService.isUserBlocked(userId)) {
            throw new ApiException("You are temporarily blocked due to suspicious activity.");
        }

        // Continue normal booking logic
        return ResponseEntity.ok(bookingService.createBooking(request));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<BookingResponse> cancel(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.cancelBooking(id));
    }

    @GetMapping
    public List<BookingResponse> myBookings() {
        return bookingService.getMyBookings();
    }
}
