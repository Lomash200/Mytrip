package com.lomash.mytrip.service.impl;

import com.lomash.mytrip.dto.hotel.HotelRequest;
import com.lomash.mytrip.dto.hotel.HotelResponse;
import com.lomash.mytrip.entity.Hotel;
import com.lomash.mytrip.entity.Location;
import com.lomash.mytrip.exception.ApiException;
import com.lomash.mytrip.repository.HotelRepository;
import com.lomash.mytrip.repository.LocationRepository;
import com.lomash.mytrip.service.HotelService;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final LocationRepository locationRepository;

    public HotelServiceImpl(HotelRepository hotelRepository, LocationRepository locationRepository) {
        this.hotelRepository = hotelRepository;
        this.locationRepository = locationRepository;
    }

    private HotelResponse mapToResponse(Hotel hotel) {
        return HotelResponse.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .description(hotel.getDescription())
                .address(hotel.getAddress())
                .rating(hotel.getRating())
                .locationName(hotel.getLocation().getName())
                .build();
    }

    @Override
    @Transactional
    public HotelResponse createHotel(HotelRequest request) {
        Location location = locationRepository.findById(request.getLocationId())
                .orElseThrow(() -> new ApiException("Invalid location"));

        Hotel hotel = Hotel.builder()
                .name(request.getName())
                .description(request.getDescription())
                .address(request.getAddress())
                .rating(request.getRating())
                .location(location)
                .build();

        return mapToResponse(hotelRepository.save(hotel));
    }

    @Override
    @Transactional
    public HotelResponse updateHotel(Long id, HotelRequest request) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ApiException("Hotel not found"));

        if (request.getName() != null) hotel.setName(request.getName());
        if (request.getDescription() != null) hotel.setDescription(request.getDescription());
        if (request.getAddress() != null) hotel.setAddress(request.getAddress());
        if (request.getRating() != 0) hotel.setRating(request.getRating());

        if (request.getLocationId() != null) {
            Location location = locationRepository.findById(request.getLocationId())
                    .orElseThrow(() -> new ApiException("Invalid location"));
            hotel.setLocation(location);
        }

        return mapToResponse(hotelRepository.save(hotel));
    }

    @Override
    @Transactional
    public void deleteHotel(Long id) {
        if (!hotelRepository.existsById(id)) {
            throw new ApiException("Hotel not found");
        }
        hotelRepository.deleteById(id);
    }

    @Override
    public HotelResponse getHotelById(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ApiException("Hotel not found"));
        return mapToResponse(hotel);
    }

    @Override
    public Page<HotelResponse> getAllHotels(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Hotel> hotels = hotelRepository.findAll(pageable);

        return hotels.map(this::mapToResponse);
    }

    @Override
    public void addHotelImage(Long hotelId, String url) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ApiException("Hotel not found"));

        hotel.getImages().add(url);
        hotelRepository.save(hotel);
    }


}
