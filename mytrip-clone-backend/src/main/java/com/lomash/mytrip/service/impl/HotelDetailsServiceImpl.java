package com.lomash.mytrip.service.impl;

import com.lomash.mytrip.dto.hotel.HotelDetailsResponse;
import com.lomash.mytrip.dto.room.RoomDetailsDto;
import com.lomash.mytrip.entity.Booking;
import com.lomash.mytrip.entity.Hotel;
import com.lomash.mytrip.entity.Room;
import com.lomash.mytrip.repository.BookingRepository;
import com.lomash.mytrip.repository.HotelRepository;
import com.lomash.mytrip.service.HotelDetailsService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelDetailsServiceImpl implements HotelDetailsService {

    private final HotelRepository hotelRepository;
    private final BookingRepository bookingRepository;

    public HotelDetailsServiceImpl(HotelRepository hotelRepository,
                                   BookingRepository bookingRepository) {
        this.hotelRepository = hotelRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public HotelDetailsResponse getHotelDetails(Long hotelId, LocalDate checkIn, LocalDate checkOut) {

        Hotel hotel = hotelRepository.findHotelWithRooms(hotelId);
        if (hotel == null) return null;

        List<RoomDetailsDto> roomDtos = hotel.getRooms().stream().map(room -> {

            boolean available =
                    bookingRepository.findByRoomIdAndCheckOutDateGreaterThanAndCheckInDateLessThan(
                            room.getId(), checkIn, checkOut
                    ).isEmpty();

            return new RoomDetailsDto(
                    room.getId(),
                    room.getRoomType(),
                    room.getPricePerNight(),
                    room.getCapacity(),
                    available,
                    room.getImageUrl()
            );
        }).collect(Collectors.toList());

        return new HotelDetailsResponse(
                hotel.getId(),
                hotel.getName(),
                hotel.getCity(),
                hotel.getAddress(),
                hotel.getRating(),
                hotel.getStarCategory(),
                hotel.getDescription(),
                hotel.getImageUrl(),
                hotel.getAmenities(),
                roomDtos
        );
    }
}
