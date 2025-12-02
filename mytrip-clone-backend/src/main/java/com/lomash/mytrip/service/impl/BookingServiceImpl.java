package com.lomash.mytrip.service.impl;

import com.lomash.mytrip.dto.booking.BookingRequest;
import com.lomash.mytrip.dto.booking.BookingResponse;
import com.lomash.mytrip.entity.Booking;
import com.lomash.mytrip.entity.Room;
import com.lomash.mytrip.repository.*;

import com.lomash.mytrip.service.AuthService;
import com.lomash.mytrip.service.BookingService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final PaymentRecordRepository paymentRecordRepository;
    private final AuthService authService;

    @PersistenceContext
    private EntityManager em;

    private static final double GST_PERCENT = 0.18;
    private static final double SERVICE_FEE = 149.0;
    private static final double CONVENIENCE_FEE = 99.0;
    private static final long RESERVATION_MINUTES = 15L;

    public BookingServiceImpl(BookingRepository bookingRepository,
                              RoomRepository roomRepository,
                              UserRepository userRepository,
                              PaymentRecordRepository paymentRecordRepository,
                              AuthService authService) {

        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.paymentRecordRepository = paymentRecordRepository;
        this.authService = authService;
    }

    // ============================
    // CREATE BOOKING (LOGGED USER)
    // ============================
    @Override
    @Transactional
    public BookingResponse createBooking(BookingRequest request, Long loggedUserId) {
        return createBookingInternal(request, loggedUserId);
    }

    // ============================
    // CREATE BOOKING (GUEST)
    // ============================
    @Override
    @Transactional
    public BookingResponse createBooking(BookingRequest request) {
        return createBookingInternal(request, null);
    }

    // ============================
    // INTERNAL BOOKING LOGIC
    // ============================
    private BookingResponse createBookingInternal(BookingRequest request, Long loggedUserId) {

        LocalDate checkIn = request.getCheckInDate();
        LocalDate checkOut = request.getCheckOutDate();

        if (checkIn == null || checkOut == null || !checkOut.isAfter(checkIn)) {
            return BookingResponse.failed("Invalid check-in/check-out dates");
        }

        Optional<Room> optionalRoom = roomRepository.findByIdForUpdate(request.getRoomId());
        if (optionalRoom.isEmpty()) {
            return BookingResponse.failed("Room not found");
        }

        Room room = optionalRoom.get();

        if (request.getGuests() > room.getCapacity()) {
            return BookingResponse.failed("Guest count exceeds room capacity");
        }

        List<Booking> overlapping =
                bookingRepository.findByRoomIdAndCheckOutDateGreaterThanAndCheckInDateLessThan(
                        room.getId(), checkIn, checkOut);

        if (!overlapping.isEmpty()) {
            return BookingResponse.failed("Room is not available");
        }

        // Nights
        long nights = Math.max(1,
                Duration.between(checkIn.atStartOfDay(), checkOut.atStartOfDay()).toDays()
        );

        // Price breakdown
        double baseAmount = room.getPricePerNight() * nights;
        double gstAmount = baseAmount * GST_PERCENT;
        double finalAmount = baseAmount + gstAmount + SERVICE_FEE + CONVENIENCE_FEE;

        Instant expiresAt = Instant.now().plus(RESERVATION_MINUTES, ChronoUnit.MINUTES);

        Booking booking = Booking.builder()
                .hotel(room.getHotel())
                .room(room)
                .checkInDate(checkIn)
                .checkOutDate(checkOut)
                .guests(request.getGuests())
                .totalAmount(finalAmount)
                .referenceCode(generateReferenceCode())
                .paymentStatus("PENDING_PAYMENT")
                .reservationExpiresAt(expiresAt)
                .build();

        if (loggedUserId != null) {
            userRepository.findById(loggedUserId).ifPresent(booking::setUser);
        }

        Booking saved = bookingRepository.save(booking);

        return BookingResponse.success(
                saved.getId(),
                saved.getReferenceCode(),
                saved.getHotel().getId(),
                saved.getRoom().getId(),
                checkIn,
                checkOut,
                saved.getGuests(),
                baseAmount,
                gstAmount,
                SERVICE_FEE,
                CONVENIENCE_FEE,
                finalAmount,
                "Room reserved! Complete payment before: " + expiresAt
        );
    }

    // ============================
    // USER BOOKINGS
    // ============================
    @Override
    public List<BookingResponse> getMyBookings() {
        Long userId = authService.getLoggedUser().getId();

        return bookingRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ============================
    // ADMIN ALL BOOKINGS
    // ============================
    @Override
    public List<BookingResponse> getAllBookings() {
        return bookingRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private BookingResponse mapToResponse(Booking b) {
        return BookingResponse.successList(
                b.getId(),
                b.getReferenceCode(),
                b.getHotel().getId(),
                b.getRoom().getId(),
                b.getCheckInDate(),
                b.getCheckOutDate(),
                b.getGuests(),
                b.getTotalAmount(),
                b.getPaymentStatus()
        );
    }

    // ============================
    // CANCEL BOOKING (placeholder)
    // ============================
    @Override
    public BookingResponse cancelBooking(Long id) {
        return BookingResponse.failed("Refund logic will be added later");
    }

    // ============================
    // HELPERS
    // ============================
    private String generateReferenceCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return "BKG-" + sb;
    }
}
