package com.lomash.mytrip.service.impl;

import com.lomash.mytrip.dto.search.HotelSearchRequest;
import com.lomash.mytrip.dto.search.HotelSearchResponse;
import com.lomash.mytrip.entity.Booking;
import com.lomash.mytrip.entity.Hotel;
import com.lomash.mytrip.entity.Room;
import com.lomash.mytrip.repository.BookingRepository;
import com.lomash.mytrip.repository.HotelRepository;
import com.lomash.mytrip.service.HotelSearchService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class HotelSearchServiceImpl implements HotelSearchService {

    private final HotelRepository hotelRepository;
    private final BookingRepository bookingRepository;

    public HotelSearchServiceImpl(HotelRepository hotelRepository,
                                  BookingRepository bookingRepository) {
        this.hotelRepository = hotelRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public List<HotelSearchResponse> searchHotels(HotelSearchRequest req) {

        List<Hotel> hotels = hotelRepository.findByCityIgnoreCase(req.getCity());
        List<HotelSearchResponse> results = new ArrayList<>();

        for (Hotel hotel : hotels) {

            boolean available = false;
            double minPrice = Double.MAX_VALUE;

            for (Room room : hotel.getRooms()) {

                if (req.getGuests() > room.getCapacity()) continue;

                List<Booking> overlaps =
                        bookingRepository.findByRoomIdAndCheckOutDateGreaterThanAndCheckInDateLessThan(
                                room.getId(),
                                req.getCheckInDate(),
                                req.getCheckOutDate()
                        );

                if (overlaps.isEmpty()) {
                    available = true;
                    minPrice = Math.min(minPrice, room.getPricePerNight());
                }
            }

            if (!available) continue;

            if (req.getMinPrice() != null && minPrice < req.getMinPrice()) continue;
            if (req.getMaxPrice() != null && minPrice > req.getMaxPrice()) continue;

            results.add(
                    HotelSearchResponse.builder()
                            .hotelId(hotel.getId())
                            .hotelName(hotel.getName())
                            .city(hotel.getCity())
                            .address(hotel.getAddress())
                            .rating(hotel.getRating())
                            .starCategory(hotel.getStarCategory())
                            .startingPrice(minPrice)
                            .available(true)
                            .imageUrl(hotel.getImageUrl())
                            .build()
            );
        }

        if ("price_low".equalsIgnoreCase(req.getSortBy())) {
            results.sort(Comparator.comparingDouble(HotelSearchResponse::getStartingPrice));
        } else if ("price_high".equalsIgnoreCase(req.getSortBy())) {
            results.sort(Comparator.comparingDouble(HotelSearchResponse::getStartingPrice).reversed());
        } else if ("rating".equalsIgnoreCase(req.getSortBy())) {
            results.sort(Comparator.comparingDouble(HotelSearchResponse::getRating).reversed());
        }

        return results;
    }
}
