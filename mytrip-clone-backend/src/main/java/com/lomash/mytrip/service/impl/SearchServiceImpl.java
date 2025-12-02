package com.lomash.mytrip.service.impl;

import com.lomash.mytrip.dto.search.HotelSearchRequest;
import com.lomash.mytrip.dto.search.HotelSearchResponse;
import com.lomash.mytrip.entity.Hotel;
import com.lomash.mytrip.entity.Room;
import com.lomash.mytrip.repository.HotelRepository;
import com.lomash.mytrip.service.SearchService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {

    private final HotelRepository hotelRepository;

    public SearchServiceImpl(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public Page<HotelSearchResponse> searchHotels(HotelSearchRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        List<Hotel> hotels = hotelRepository.findByCityIgnoreCase(request.getCity());

        List<HotelSearchResponse> responses = hotels.stream()
                .filter(hotel -> filterByPrice(hotel, request.getMinPrice(), request.getMaxPrice()))
                .map(hotel -> convertToResponse(hotel, request))
                .sorted(getComparator(request.getSortBy()))
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), responses.size());

        return new PageImpl<>(
                responses.subList(start, end),
                pageable,
                responses.size()
        );
    }

    private boolean filterByPrice(Hotel hotel, Double minPrice, Double maxPrice) {
        if (hotel.getRooms() == null || hotel.getRooms().isEmpty()) {
            return false;
        }

        double minRoomPrice = hotel.getRooms().stream()
                .mapToDouble(Room::getPricePerNight)
                .min()
                .orElse(Double.MAX_VALUE);

        if (minPrice != null && minRoomPrice < minPrice) return false;
        if (maxPrice != null && minRoomPrice > maxPrice) return false;

        return true;
    }

    private HotelSearchResponse convertToResponse(Hotel hotel, HotelSearchRequest request) {
        double minPrice = hotel.getRooms().stream()
                .mapToDouble(Room::getPricePerNight)
                .min()
                .orElse(0.0);

        return HotelSearchResponse.builder()
                .hotelId(hotel.getId())
                .hotelName(hotel.getName())
                .city(hotel.getCity())
                .address(hotel.getAddress())
                .rating(hotel.getRating())
                .starCategory(hotel.getStarCategory())
                .startingPrice(minPrice)
                .available(true)
                .imageUrl(hotel.getImageUrl())
                .build();
    }

    private Comparator<HotelSearchResponse> getComparator(String sortBy) {
        if ("price_low".equalsIgnoreCase(sortBy)) {
            return Comparator.comparingDouble(HotelSearchResponse::getStartingPrice);
        } else if ("price_high".equalsIgnoreCase(sortBy)) {
            return Comparator.comparingDouble(HotelSearchResponse::getStartingPrice).reversed();
        } else if ("rating".equalsIgnoreCase(sortBy)) {
            return Comparator.comparingDouble(HotelSearchResponse::getRating).reversed();
        }
        return Comparator.comparing(HotelSearchResponse::getHotelName);
    }
}