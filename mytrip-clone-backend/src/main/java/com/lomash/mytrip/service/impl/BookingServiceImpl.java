package com.lomash.mytrip.service.impl;

import com.lomash.mytrip.dto.booking.BookingRequest;
import com.lomash.mytrip.dto.booking.BookingResponse;
import com.lomash.mytrip.entity.*;
import com.lomash.mytrip.entity.enums.BookingStatus;
import com.lomash.mytrip.exception.ApiException;
import com.lomash.mytrip.repository.*;
import com.lomash.mytrip.service.BookingService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, UserRepository userRepository,
                              HotelRepository hotelRepository, RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException("User not found"));
    }

    private BookingResponse mapToResponse(Booking b) {
        return BookingResponse.builder()
                .id(b.getId())
                .referenceCode(b.getReferenceCode())
                .status(b.getStatus().name())
                .totalAmount(b.getTotalAmount())
                .hotelName(b.getHotel().getName())
                .roomType(b.getRoom().getRoomType())
                .checkInDate(b.getCheckInDate().toString())
                .checkOutDate(b.getCheckOutDate().toString())
                .build();
    }

    @Override
    @Transactional
    public BookingResponse createBooking(BookingRequest request) {

        User user = getCurrentUser();

        Hotel hotel = hotelRepository.findById(request.getHotelId())
                .orElseThrow(() -> new ApiException("Hotel not found"));

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new ApiException("Room not found"));

        if (room.getAvailabilityCount() <= 0) {
            throw new ApiException("Room unavailable");
        }

        // Convert String -> LocalDate
        LocalDate checkIn;
        LocalDate checkOut;

        try {
            checkIn = LocalDate.parse(request.getCheckInDate());
            checkOut = LocalDate.parse(request.getCheckOutDate());
        } catch (DateTimeParseException e) {
            throw new ApiException("Invalid date format. Use yyyy-MM-dd");
        }

        if (checkOut.isBefore(checkIn)) {
            throw new ApiException("Checkout date must be after check-in date");
        }

        // price calculation (simple version)
        double total = room.getPricePerNight();

        // reduce room availability
        room.setAvailabilityCount(room.getAvailabilityCount() - 1);
        roomRepository.save(room);

        Booking booking = Booking.builder()
                .referenceCode(UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .checkInDate(checkIn)
                .checkOutDate(checkOut)
                .guests(request.getGuests())
                .totalAmount(total)
                .status(BookingStatus.PENDING)
                .hotel(hotel)
                .room(room)
                .user(user)
                .build();

        return mapToResponse(bookingRepository.save(booking));
    }

    @Override
    @Transactional
    public BookingResponse cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ApiException("Booking not found"));

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new ApiException("Already cancelled");
        }

        // increase room availability
        Room room = booking.getRoom();
        room.setAvailabilityCount(room.getAvailabilityCount() + 1);
        roomRepository.save(room);

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);

        return mapToResponse(booking);
    }

    @Override
    public List<BookingResponse> getMyBookings() {
        User user = getCurrentUser();
        return bookingRepository.findByUser(user)
                .stream().map(this::mapToResponse).toList();
    }

    @Override
    public List<BookingResponse> getAllBookings() {
        return bookingRepository.findAll()
                .stream().map(this::mapToResponse).toList();
    }
}
