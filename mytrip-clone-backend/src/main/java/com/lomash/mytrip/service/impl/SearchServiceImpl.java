package com.lomash.mytrip.service.impl;

import com.lomash.mytrip.dto.search.HotelSearchRequest;
import com.lomash.mytrip.dto.search.HotelSearchResponse;
import com.lomash.mytrip.entity.Hotel;
import com.lomash.mytrip.entity.Room;
import com.lomash.mytrip.repository.HotelRepository;
import com.lomash.mytrip.service.SearchService;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    private final HotelRepository hotelRepository;

    public SearchServiceImpl(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public Page<HotelSearchResponse> searchHotels(HotelSearchRequest request) {

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        Page<Hotel> hotels =
                hotelRepository.findByLocationId(request.getLocationId(), pageable);

        List<HotelSearchResponse> dtoList = hotels.getContent()
                .stream()
                .map(h -> {

                    double minPrice = 0.0;
                    List<Room> rooms = h.getRooms();

                    if (rooms != null && !rooms.isEmpty()) {
                        minPrice = rooms.stream()
                                .mapToDouble(Room::getPricePerNight)
                                .min()
                                .orElse(0.0);
                    }

                    return HotelSearchResponse.builder()
                            .id(h.getId())
                            .name(h.getName())
                            .locationName(h.getLocation().getName())
                            .rating(h.getRating())
                            .priceFrom(minPrice)
                            .build();
                })
                .toList();

        return new PageImpl<>(dtoList, pageable, hotels.getTotalElements());
    }
}
